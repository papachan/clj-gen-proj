(ns com.example.system.server
  (:require
   [com.example.handler :refer [app]]
   [ring.adapter.jetty :as ring-jetty]
   [ring.middleware.reload :as reload]
   [integrant.core :as ig]))

(defn reloading-ring-handler
  "Reload ring handler on each request."
  [f]
  (let [reload! (#'reload/reloader ["src"] true)]
    (fn
      ([request]
       (reload!)
       ((f) request))
      ([request respond raise]
       (reload!)
       ((f) request respond raise)))))

(defmethod ig/init-key :ring/jetty [_ {:keys [port join? dev-mode?]}]
  (let [create-handler-fn (fn [] #'app)
        handler (if dev-mode?
                  (reloading-ring-handler create-handler-fn)
                  (create-handler-fn))]
    (ring-jetty/run-jetty handler {:port port, :join? join?})))

(defmethod ig/halt-key! :ring/jetty [_ server]
  (.stop server))
