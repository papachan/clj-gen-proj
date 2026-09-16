(ns com.example.routes
  (:require
   [re-frame.core :as re-frame]
   [reitit.coercion.malli :as rcm]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.session :as session]
   [com.example.subs]
   [com.example.views :as views]))

(def ^:private routes ["/"
                       [""
                        {:name ::webapp/home
                         :view #'views/home}]
                       ["login"
                        {:name    ::webapp/login
                         :view    #'views/login
                         :public? true}]
                       ["subpage"
                        {:name ::webapp/subpage
                         :view #'views/subpage}]])

(def router
  (rf/router
    routes
    {:data {:coercion rcm/coercion}}))

(defn- public?
  [match]
  (true? (get-in match [:data :public?])))

(defn on-navigate
  [new-match]
  (when new-match
    (if (or (public? new-match) (session/active?))
      (re-frame/dispatch [::events/navigate new-match])
      ;; No session cookie: send them to /login instead of rendering the
      ;; guarded view, remembering where they were headed.
      (re-frame/dispatch [::events/redirect-to-login new-match]))))

(defn init-routes!
  "Initial setup router."
  []
  (rfe/start!
   router
   on-navigate
   ;; set to false to enable HistoryAPI
   {:use-fragment false}))
