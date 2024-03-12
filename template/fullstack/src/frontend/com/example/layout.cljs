(ns com.example.layout
  (:require
   [re-frame.core :as re-frame]
   [goog.string :as gstring]
   [goog.string.format]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs :as subs]))

(defn navigation
  []
  [:div
   {:className "px-32 py-6 mt-4 text-center bg-white"}
   [:div
    [:button
     {:on-click #(re-frame/dispatch [::events/push-state ::webapp/home])}
     "home"]
    [:button
     {:on-click #(re-frame/dispatch [::events/push-state ::webapp/subpage])}
     "subpage 1"]]])

(defn homepage
  []
  [:div
   {:className "flex items-center justify-center min-h-screen bg-gray-100"}
   (navigation)
   [:div
    {:className "px-32 py-6 mt-4 text-left bg-white"}
    [:div
     "home"]]])

(defn subpage
  []
  [:div
   {:className "flex items-center justify-center min-h-screen bg-gray-100"}
   (navigation)
   [:div
    {:className "px-32 py-6 mt-4 text-left bg-white"}
    [:div
     "Subpage 1"]]])
