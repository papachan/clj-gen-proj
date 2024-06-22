(ns com.example.util)

(defn factorial [n]
  (if (zero? n)
    1
    (* n (factorial (dec n)))))
