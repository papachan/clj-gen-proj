(ns com.example.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.frontend.easy :as rfe]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs :as subs]
   [com.example.layout :as layout]))

(def routes
  ["/"
   ["home"
    {:name ::webapp/home
     :view #'layout/homepage
     :controllers []}]])
