(ns com.example.app
  (:require
   ["react-dom/client" :refer [createRoot]]
   [day8.re-frame.http-fx]
   [re-frame.core :as re-frame]
   [reagent.core :as r]
   [reitit.coercion.malli :as rsm]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [com.example.debug :refer [debug?]]
   [com.example.events :as events]
   [com.example.myapp]
   [com.example.routes :as routes]
   [com.example.subs :as subs]))

(defn router-component [{:keys [router]}]
  (let [current-route @(re-frame/subscribe [::subs/current-route])
        view (-> current-route :data :view)]
    (when view
      [view {:router        routes/router
             :current-route current-route}])))

;;; Setup ;;;
(defonce ^:private root (createRoot (js/document.getElementById "app")))

(defn render []
  (.render root (r/as-element [router-component {:router routes/router}])))

(defn ^:export re-render []
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (re-frame/clear-subscription-cache!)
  (render))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (.log js/console "stop"))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (routes/init-routes!)
  (render))
