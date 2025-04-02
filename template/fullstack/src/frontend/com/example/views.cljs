(ns com.example.views
  (:require
   [clojure.string :as str]
   [re-frame.core :as re-frame]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs]))


(defn navigation
  [page-selected]
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
         :className (str (if (= "home" page-selected)
                           "text-blue-600" "underline") " hover:text-gray-300 text-3xl font-bold")}
        "home"]]
      [:li
       [:a
        {:href      "#"
         :on-click  #(re-frame/dispatch [::events/push-state {:route ::webapp/subpage}])
         :className (str (if (str/starts-with?  page-selected "subpage")
                           "text-blue-600" "underline") " hover:text-gray-300 text-3xl font-bold")}
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
   (navigation "home")
   (container "home")])

(defn subpage
  []
  [:div
   {:className "bg-gray-100"}
   (navigation "subpage 1")
   (container "subpage 1")])
