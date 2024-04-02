(ns com.example.app
  (:require
   ["react-dom/client" :refer [createRoot]]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]))

(defn my-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn render []
  (rdom/render [my-component]
               (js/document.getElementById "app")))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (.log js/console "stop"))

(defn ^:dev/after-load re-render []
  (js/console.log "reload")
  (render))

(defn ^:export init []
  (js/console.log "start")
  (render))
