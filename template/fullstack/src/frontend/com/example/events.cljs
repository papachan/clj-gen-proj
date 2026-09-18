(ns com.example.events
  (:require
   [ajax.core :as ajax]
   [clojure.string :as str]
   [day8.re-frame.http-fx]
   [re-frame.core :as re-frame]
   [reitit.frontend.controllers :as rfc]
   [reitit.frontend.easy :as rfe]
   [com.example.db :refer [app-db]]
   [com.example.debug :refer [debug?]]
   [com.example.myapp :as webapp]
   [com.example.session :as session]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   ;; A page refresh throws away app-db, so rebuild :auth from the cookie.
   (assoc app-db :auth (when-let [t (session/token)]
                         {:username nil :token t}))))

;; navigation

(re-frame/reg-fx
 :push-state
 (fn [{:keys [route]}]
   (rfe/push-state route)))

(re-frame/reg-event-fx
 ::push-state
 (fn [_ [_ & route]]
   {:push-state route}))

;; Used for redirects, so the bounced-off URL does not land in history.
(re-frame/reg-fx
 :replace-state
 (fn [{:keys [route]}]
   (rfe/replace-state route)))

(re-frame/reg-fx
 :set-session-cookie
 (fn [value]
   (session/set-token! value)))

(re-frame/reg-fx
 :clear-session-cookie
 (fn [_]
   (session/clear!)))

(re-frame/reg-event-fx
 ::redirect-to-login
 (fn [{:keys [db]} [_ attempted-match]]
   {:db            (assoc db :redirect-to (get-in attempted-match [:data :name]))
    :replace-state {:route ::webapp/login}}))

(re-frame/reg-event-db
  ::navigate
  (fn [{:keys [current-route] :as db} [_ new-route]]
    (let [old-controllers (:controllers current-route)
          new-route* (assoc new-route :controllers (rfc/apply-controllers
                                                     old-controllers
                                                     new-route))]
      (assoc db :current-route new-route*))))

(re-frame/reg-event-db
 ::set-login-field
 (fn [db [_ field value]]
   (-> db
       (assoc-in [:login field] value)
       (assoc-in [:login :error] nil))))

(re-frame/reg-event-fx
 ::login-submit
 (fn [{:keys [db]} _]
   (let [{:keys [username password]} (:login db)]
     (if (or (str/blank? username) (str/blank? password))
       {:db (assoc-in db [:login :error] "Please fill in both username and password.")}
       {:db         (-> db
                        (assoc-in [:login :submitting?] true)
                        (assoc-in [:login :error] nil))
        :http-xhrio {:method          :post
                     :uri             "/api/v1/users/login"
                     :params          {:username username
                                       :password password}
                     :format          (ajax/json-request-format)
                     :response-format (ajax/json-response-format {:keywords? true})
                     :on-success      [::login-success]
                     :on-failure      [::login-failure]}}))))

(re-frame/reg-event-fx
 ::login-success
 (fn [{:keys [db]} [_ {:keys [success token error]}]]
   (if success
     {:db                 (-> db
                              (assoc :auth {:username (get-in db [:login :username])
                                            :token    token})
                              (assoc :redirect-to nil)
                              (assoc :login (:login app-db)))
      ;; TODO: Drop this once the backend issues the session cookie itself
      :set-session-cookie (or token "1")
      :replace-state      {:route (or (:redirect-to db) ::webapp/home)}}
     {:db (-> db
              (assoc-in [:login :submitting?] false)
              (assoc-in [:login :error] (or error "Invalid username or password.")))})))

(re-frame/reg-event-db
 ::login-failure
 (fn [db [_ {:keys [status response status-text]}]]
   (let [message (cond
                   (zero? status)      "Cannot reach the server. Is the backend running?"
                   (= 401 status)      "Invalid username or password."
                   (:error response)   (:error response)
                   (seq status-text)   (str "Login failed (" status "): " status-text)
                   :else               (str "Login failed with status " status "."))]
     (-> db
         (assoc-in [:login :submitting?] false)
         (assoc-in [:login :error] message)))))

(re-frame/reg-event-fx
 ::logout
 (fn [{:keys [db]} _]
   {:db                   (-> db
                              (assoc :auth nil)
                              (assoc :redirect-to nil)
                              (assoc :login (:login app-db)))
    :clear-session-cookie true
    :replace-state        {:route ::webapp/login}}))
