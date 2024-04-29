(ns com.example.main
  (:require
   [com.example.system :as sys :refer [config]])
  (:gen-class))

(defn -dev-main
  [& _]
  (println "dev mode")
  (println "server running in port 3000")
  (sys/start (update-in config [:ring/jetty :dev-mode?] not)))

(defn -main
  [& _]
  (println "server running in port 3000")
  (sys/start config))
