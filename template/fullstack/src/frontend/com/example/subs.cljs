(ns com.example.subs
  (:require
   [re-frame.core :as re-frame :refer [reg-sub]]
   [com.example.myapp :as webapp]))


(reg-sub
 ::current-route
 (fn [db]
   (:current-route db)))

(reg-sub
 ::login-form
 (fn [db]
   (:login db)))

(reg-sub
 ::login-error
 :<- [::login-form]
 (fn [login]
   (:error login)))

(reg-sub
 ::login-submitting?
 :<- [::login-form]
 (fn [login]
   (boolean (:submitting? login))))

(reg-sub
 ::auth
 (fn [db]
   (:auth db)))

(reg-sub
 ::authenticated?
 :<- [::auth]
 (fn [auth]
   (some? auth)))
