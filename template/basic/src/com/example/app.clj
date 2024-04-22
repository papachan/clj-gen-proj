(ns com.example.app
  (:gen-class))

(defn function-called-from-command-line
  [data]
  (println (:name data) "clojure!"))

(defn -main [& args]
  (println "something"))
