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
   [com.example.myapp]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   app-db))

;; navigation

(re-frame/reg-fx
 :push-state
 (fn [{:keys [route]}]
   (rfe/push-state route)))

(re-frame/reg-event-fx
 ::push-state
 (fn [_ [_ & route]]
   {:push-state route}))

(re-frame/reg-event-db
  ::navigate
  (fn [{:keys [current-route] :as db} [_ new-route]]
    (let [old-controllers (:controllers current-route)
          new-route* (assoc new-route :controllers (rfc/apply-controllers
                                                     old-controllers
                                                     new-route))]
      (assoc db :current-route new-route*))))
