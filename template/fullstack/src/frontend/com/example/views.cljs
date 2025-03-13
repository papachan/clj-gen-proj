(ns com.example.views
  (:require
   [re-frame.core :as re-frame]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs]))


(defn navigation
  []
  [:header
   [:nav
    {:className "bg-gray-200 p-6"}
    [:div
     {:className "container mx-auto"}
     [:ul
      {:className "flex justify-around"}
      [:li
       [:a
        {:href      "#"
         :on-click  #(re-frame/dispatch [::events/push-state {:route ::webapp/home}])
         :className "hover:text-gray-300"}
        "home"]]
      [:li
       [:a
        {:href      "#"
         :on-click  #(re-frame/dispatch [::events/push-state {:route ::webapp/subpage}])
         :className "hover:text-gray-300"}
        "subpage 1"]]]]]])

(defn container
  [name]
  [:main
   {:className "container mx-auto mt-10"}
   [:div
    {:className "flex justify-center items-center h-screen"}
    [:div
     {:className "bg-white p-10 rounded-lg shadow-lg"}
     [:h1
      {:className "text-2xl font-bold mb-4"}
      name]
     [:p
      {:className "text-gray-700"}
      "Lorem ipsum"]]]])

(defn home
  []
  [:div
   {:className "bg-gray-100"}
   (navigation)
   (container "home")])

(defn subpage
  []
  [:div
   {:className "bg-gray-100"}
   (navigation)
   (container "subpage 1")])
