(ns com.example.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.frontend.easy :as rfe]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs :as subs]))

(defn homepage
  []
  [:div
   {:className "flex items-center justify-center min-h-screen bg-gray-100"}
   [:div
    {:className "px-32 py-6 mt-4 text-left bg-white shadow-lg"}
    [:div
     "Here"]]])

(def routes
  ["/"
   ["home"
    {:name ::webapp/home
     :view homepage}]])
