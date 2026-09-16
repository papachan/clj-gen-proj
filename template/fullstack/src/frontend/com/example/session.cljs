(ns com.example.session
  "The browser cookie that marks an active login session.

  IMPORTANT: `document.cookie` cannot see a cookie the server sets with
  HttpOnly. If the backend later issues the session cookie itself, it must
  either leave it readable, or set a separate non-HttpOnly marker cookie
  next to it, or the guard here has to be replaced by a `/me` style call
  that asks the server whether the session is still valid."
  (:require
   [clojure.string :as str]))

(def cookie-name "session")

(def ^:private one-day-in-seconds (* 60 60 24))

(defn- raw-cookies []
  (or (.-cookie js/document) ""))

(defn- parse
  "\"a=1; b=2\" -> {\"a\" \"1\" \"b\" \"2\"}"
  [s]
  (into {}
        (comp (map str/trim)
              (remove str/blank?)
              (keep (fn [pair]
                      (when-let [i (str/index-of pair "=")]
                        (when (pos? i)
                          [(js/decodeURIComponent (subs pair 0 i))
                           (js/decodeURIComponent (subs pair (inc i)))])))))
        (str/split s #";")))

(defn token
  "Value of the session cookie, or nil when there is none."
  []
  (get (parse (raw-cookies)) cookie-name))

(defn active?
  "True when the browser is holding a login session cookie."
  []
  (some? (token)))

(defn set-token!
  ([value] (set-token! value one-day-in-seconds))
  ([value max-age]
   (set! (.-cookie js/document)
         (str cookie-name "=" (js/encodeURIComponent value)
              "; path=/; max-age=" max-age "; samesite=lax"))))

(defn clear! []
  (set! (.-cookie js/document)
        (str cookie-name "=; path=/; max-age=0; samesite=lax")))
