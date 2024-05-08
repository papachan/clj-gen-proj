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

(defmethod ig/init-key :ring/jetty [_ {:keys [dev-mode?] :as opts}]
  (let [handler (when dev-mode?
                  (reloading-ring-handler (fn [] #'app)))]
    (ring-jetty/run-jetty (if dev-mode?
                            handler
                            app) (dissoc opts :dev-mode?))))

(defmethod ig/halt-key! :ring/jetty [_ server]
  (.stop server))
