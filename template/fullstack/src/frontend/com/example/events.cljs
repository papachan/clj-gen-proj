(ns com.example.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [re-frame.core :as re-frame]
   [reitit.frontend.controllers :as rfc]
   [reitit.frontend.easy :as rfe]
   [com.example.db :refer [app-db]]
   [com.example.debug :refer [debug?]]
   [com.example.myapp :as webapp]))

(re-frame/reg-event-db
  ::initialize-db
  (fn [db _]
    app-db))

;; navigation

(re-frame/reg-fx
  :push-state
  (fn [route]
    (apply rfe/push-state route)))

(re-frame/reg-event-fx
  ::push-state
  (fn [_ [_ & route]]
    {:push-state route}))

(re-frame/reg-event-db
  ::navigated
  (fn [db [_ new-match]]
    (let [old-match   (:current-route db)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :current-route (assoc new-match :controllers controllers)))))
