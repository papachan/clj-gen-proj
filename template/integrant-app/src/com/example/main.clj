(ns com.example.main
  (:require
   [com.example.system :as sys :refer [config]])
  (:gen-class))

(defn -dev-main
  [args]
  (println "dev mode enabled")
  (println "server running on port" (:port args))
  (sys/start (update-in config [:ring/jetty] assoc :port (:port args) :dev-mode? true)))

(defn -main
  [& _]
  (println "server running on port" (get-in config [:ring/jetty :port]))
  (sys/start config))
