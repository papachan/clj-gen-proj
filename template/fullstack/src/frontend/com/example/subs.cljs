(ns com.example.subs
  (:require
   [re-frame.core :as re-frame :refer [reg-sub]]
   [com.example.myapp :as webapp]))


(reg-sub
 ::current-route
 (fn [db]
   (:current-route db)))
