(ns com.example.main
  (:require
   [com.example.system :as sys :refer [config]])
  (:gen-class))

(defn -dev-main
  [& _]
  (println "dev mode enabled")
  (println "server running in port" (get-in config [:ring/jetty :port]))
  (sys/start (update-in config [:ring/jetty :dev-mode?] not)))

(defn -main
  [& _]
  (println "server running in port" (get-in config [:ring/jetty :port]))
  (sys/start config))
