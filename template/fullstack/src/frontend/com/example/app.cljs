(ns com.example.app
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [com.something.debug :refer [debug?]]))

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))

(defn simple-component []
  [:div
   [:p "I am a text in " [:strong "bold!"]]
   [:p.someclass
    "Hello from "
    [:strong
     [:span {:style {:color "red"}} "Clojurescript!"]]]])

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch [::set-name])
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [simple-component] root-el)))

(defn ^:export init []
  (re-frame/dispatch-sync [::initialize-db])
  (dev-setup)
  (mount-root))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
