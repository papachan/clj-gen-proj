(ns com.example.app
  (:require
   [reagent.dom :as rdom]
   ["react-dom/client" :refer [createRoot]]
   [reagent.core :as r]))

(defonce root (createRoot (js/document.getElementById "app")))

(defn my-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn render []
  (.render root (r/as-element [my-component])))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (.log js/console "stop"))

(defn ^:dev/after-load re-render []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (.log js/console "reload")
  (render))

(defn ^:export init []
  (.log js/console "start")
  (render))
