(ns com.example.app
  (:gen-class))

(defn function-called-from-command-line
  [data]
  (println (str (:args1 data) "!")))

(defn -main [& args]
  (println "something"))
