(ns com.example.db
  (:require [com.example.myapp]))

(def app-db
  "Main state for the app"
  {:data {}
   :push-state nil
   :current-route nil})
