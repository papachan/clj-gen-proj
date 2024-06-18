(ns com.example.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.coercion.malli :as rcm]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs]
   [com.example.views :as views]))

(def ^:private routes ["/"
                       [""
                        {:name      ::webapp/home
                         :view      #'views/home
                         :link-text ""}]
                       ["subpage"
                        {:name      ::webapp/subpage
                         :view      #'views/subpage
                         :link-text ""}]])

(def router
  (rf/router
    routes
    {:data {:coercion rcm/coercion}}))

(defn on-navigate
  [new-match]
  (when new-match
    (re-frame/dispatch [::events/navigate new-match])))

(defn init-routes!
  "Initial setup router."
  []
  (rfe/start!
   router
   on-navigate
   ;; set to false to enable HistoryAPI
   {:use-fragment false}))
