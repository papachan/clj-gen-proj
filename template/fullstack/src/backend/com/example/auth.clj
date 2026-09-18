(ns com.example.auth
  (:require
   [com.example.config :refer [config]]))

(def session-cookie-name "jwt-session")

;; A readable companion cookie carrying no secret. It exists purely so the
;; ClojureScript route guard can tell whether a session is active -- it cannot
;; see the HttpOnly cookie above.
(def marker-cookie-name "session")

(defn- auth-config []
  (get-in config [:app :auth]))

(defn ttl-seconds []
  (:session-ttl-seconds (auth-config) 86400))

(defn- now-seconds []
  (quot (System/currentTimeMillis) 1000))

(defn find-user [username]
  (get (:users (auth-config)) username))

(defn authenticate
  "The user map (minus its hash) when the credentials are good, else nil."
  [username password]
  ;; used by login-handler
  ;; TODO: add the logic to verify the user data
  nil)

(defn create-token
  [user]
  ;; TODO: add the logic here to sign the jwt token
  :ok)

(defn verify-token
  "Decoded claims, or nil if the token is missing, tampered with or expired."
  [token]
  (when token
    (try
      ;; TODO: Add the logic to unsign the jwt token
      :ok
      (catch Exception _ nil))))

(defn- base-cookie [max-age]
  {:path      "/"
   :same-site :lax
   :max-age   max-age
   :secure    (boolean (:secure-cookies? (auth-config)))})

(defn session-cookies
  [token]
  (let [ttl (ttl-seconds)]
    {session-cookie-name (assoc (base-cookie ttl) :value token :http-only true)
     marker-cookie-name  (assoc (base-cookie ttl) :value "1")}))

(defn expired-cookies []
  {session-cookie-name (assoc (base-cookie 0) :value "" :http-only true)
   marker-cookie-name  (assoc (base-cookie 0) :value "")})

(defn identity-for
  "Claims for the session cookie on this request, or nil."
  [request]
  (verify-token (get-in request [:cookies session-cookie-name :value])))
