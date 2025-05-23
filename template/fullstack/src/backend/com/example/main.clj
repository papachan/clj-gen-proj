(ns com.example.main
  (:require
   [com.example.config]
   [com.example.handler :refer [app]]
   [com.example.middleware :refer [reloading-ring-handler]]
   [mount.core :as mount :refer [defstate]]
   [ring.adapter.jetty :as jetty :refer [run-jetty]])
  (:gen-class))

(defn start
  [{:keys [dev-mode? server-options]}]
  (let [create-handler-fn (fn [] #'app)
        handler* (if dev-mode?
                   (reloading-ring-handler create-handler-fn)
                   (create-handler-fn))]
    (run-jetty handler* server-options)))

(defstate ^{:on-reload :noop} jetty
  :start (start (mount/args))
  :stop (.stop jetty))

(defn -dev-main
  [args]
  (mount/start-with-args {:dev-mode? true
                          :server-options args})
  (println "server running on port" (:port args)))

(defn -main
  [& _]
  (mount/start-with-args
   {:dev-mode? false
    :server-options {:port 3000 :join? false}})
  (println "server running on port 3000"))
