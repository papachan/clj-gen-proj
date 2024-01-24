(ns com.example.responses
  (:require
   [ring.util.response :as resp]))

(defn response [status-code msg]
  (resp/status (resp/response msg) status-code))
