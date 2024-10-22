(ns com.example.main
  (:require
   [com.example.config]
   [com.example.handler :refer [app]]
   [mount.core :as mount :refer [defstate]]
   [ring.adapter.jetty :as jetty :refer [run-jetty]]
   [ring.middleware.reload :as reload])
  (:gen-class))

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

(defn start
  [{:keys [dev-mode?]
    {:keys [port]} :server-options}]
  (let [create-handler-fn (fn [] #'app)
        handler* (if dev-mode?
                   (reloading-ring-handler create-handler-fn)
                   (create-handler-fn))]
    (run-jetty handler* {:port port, :join? false})
    (println "server running on port" port)))

(defstate ^{:on-reload :noop} jetty
  :start (start {:dev-mode? false
                 :server-options {:port 3000 :join? false}})
  :stop (.stop jetty))

(defn -dev-main
  [& _]
  (mount/start-without #'jetty)
  (start {:dev-mode? true
          :server-options {:port 3000 :join? false}}))

(defn -main
  [& _]
  (mount/start))
