(ns com.example.system
  (:require
   [integrant.core :as ig]
   [com.example.system.server]))

(def config {:ring/jetty {:port 3000
                          :join? false
                          :dev-mode? false}})

(comment
  (ig/init (ig/prep config)))

(defn start
  [config]
  (ig/init config))

(comment
  (def system (ig/init config))

  (ig/halt! system)

  ,)
