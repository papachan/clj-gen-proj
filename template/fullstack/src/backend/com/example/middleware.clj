(ns com.example.middleware
  (:require
   [com.example.auth :as auth]
   [com.example.responses :refer [response]]
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

(defn wrap-authenticated
  "Rejects the request with 401 unless it carries a valid session cookie.
  On success the decoded claims are attached to the request as :identity."
  [handler]
  (fn [request]
    (if-let [claims (auth/identity-for request)]
      (handler (assoc request :identity claims))
      (response 401 {:success false
                     :error   "Not authenticated."}))))
