(ns com.example.main
  (:gen-class))

(defn function-called-from-command-line
  [data]
  (println (str "Running in dev mode" (:args1 data) ".")))

(defn -main [& args]
  (println "starting api"))
