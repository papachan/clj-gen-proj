(ns com.example.app
  (:require
   ["react-dom/client" :refer [createRoot]]
   [reagent.core :as r]
   [com.example.util :refer [factorial]]))

(defonce root (createRoot (js/document.getElementById "app")))

(defn my-component []
  [:div
   {:className "d-flex align-items-center justify-content-center vh-100"}
   [:div
    {:className "text-center"}
    [:p "I am a component!!!!"]
    [:p.someclass
     "I have " [:strong "bold"]
     [:span
      {:style {:color "red"}} " and red "] "text."]]])

(defn render []
  (.render root (r/as-element [my-component])))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (.log js/console "stop"))

(defn ^:export re-render []
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (.log js/console "reload")
  (render))

(defn ^:export init []
  (.log js/console "start")
  (js/console.log "Main executed. Factorial of 5 is : " (factorial 5))
  (render))
