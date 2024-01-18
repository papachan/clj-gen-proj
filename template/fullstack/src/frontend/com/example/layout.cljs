(ns com.example.layout
  (:require
   [re-frame.core :as re-frame]
   [goog.string :as gstring]
   [goog.string.format]
   [com.something.events :as events]
   [com.something.myapp :as webapp]
   [com.something.subs :as subs]))

(defn navigation
  []
  [:div
   {:className "flex items-center justify-center min-h-screen bg-gray-100"}
   [:div
    {:className "px-32 py-6 mt-4 text-left bg-white"}
    [:div
     [:button
      {:on-click #(re-frame/dispatch [::push-state ::webapp/home])}
      "Go to home"]]
    [:div
     [:button
      {:on-click #(re-frame/dispatch [::push-state ::webapp/subpage])}
      "Go to sub page"]]]])

(defn homepage
  []
  [:div
   {:className "flex items-center justify-center min-h-screen bg-gray-100"}
   [:div
    {:className "px-32 py-6 mt-4 text-left bg-white"}
    [:div
     "Something here"]]])

(defn subpage
  []
  [:div
   {:className "flex items-center justify-center min-h-screen bg-gray-100"}
   [:div
    {:className "px-32 py-6 mt-4 text-left bg-white"}
    [:div
     "Subpage 1"]]])
