(ns com.example.app
  (:require
   ["react-dom/client" :refer [createRoot]]
   [day8.re-frame.http-fx]
   [goog.dom :as gdom]
   [re-frame.core :as re-frame]
   [reagent.core :as r]
   [reitit.coercion.spec :as rss]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [com.example.debug :refer [debug?]]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.routes :as myroutes]
   [com.example.subs :as subs]
   [com.example.layout :as layout]))

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::events/navigated new-match])))

(defn router-component [{:keys [router]}]
  (let [current-route @(re-frame/subscribe [::subs/current-route])]
    (if current-route
      [(-> current-route :data :view)]
      (re-frame/dispatch [::events/push-state ::webapp/home]))))

;;; Routes ;;;

(def router
  (rf/router
   myroutes/routes
   {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (.log js/console "initialization routes")
  (rfe/start!
   router
   on-navigate
   {:use-fragment true}))

;;; Setup ;;;
(defonce root (createRoot (gdom/getElement "app")))

(defn render []
  (.render root (r/as-element [router-component {:router router}])))

(defn ^:dev/after-load re-render []
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
  (.log js/console "start")
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (init-routes!)
  (render))
