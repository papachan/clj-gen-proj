(ns com.example.responses
  (:require
   [clojure.data.json :as json]))

(defn http-ok
  [msg]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str {:message msg})})
