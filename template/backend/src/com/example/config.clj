(ns com.example.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [mount.core :refer [defstate]]))

(defn- read-config []
  (aero/read-config (io/resource "config.edn")))

(defstate config
  :start (read-config))
