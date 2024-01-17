(ns com.example.middleware
  (:require
   [ring.middleware.reload :as reload]))

(defn reloading-ring-handler
  "Reload ring handler on each request."
  [f]
  (let [reload! (#'reload/reloader ["src"] true)]
    (fn
      ([request]
       (reload!)
       ((f) request))
      ([request respond raise]
       (reload!)
       ((f) request respond raise)))))
