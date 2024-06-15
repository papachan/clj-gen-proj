(ns com.example.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.coercion.malli :as reitit-malli]
   [reitit.frontend :as reitit-front]
   [reitit.frontend.easy :as rfe]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs :as subs]
   [com.example.layout :as layout]))

(def ^:private routes ["/"
                       ["home"
                        {:name ::webapp/home
                         :view #'layout/homepage
                         :controllers []}]
                       ["subpage"
                        {:name ::webapp/subpage
                         :view #'layout/subpage
                         :controllers []}]])

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::events/navigated new-match])))

(def router
  (reitit-front/router
    routes
    {:data {:coercion reitit-malli/coercion}}))

(defn init-routes! []
  (.log js/console "initialization routes")
  (rfe/start!
   router
   on-navigate
   ;; set to false to enable HistoryAPI
   {:use-fragment true}))
