(ns user
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.java.javadoc :refer [javadoc]]
            [clojure.pprint :refer [pprint]]
            [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :as r]
            [mount.core :as mount]
            [com.example.main :as main]))


(r/set-refresh-dirs "src/backend" "env/dev")

(defn reset
  []
  (r/refresh))

(defn start-server []
  (main/-dev-main {:port 8080 :join? false}))

(defn stop-server []
  (mount/stop))

(comment
  ;; Refresh changed namespaces
  (r/refresh-all)

  ;; start server
  (r/refresh :after 'com.example.main/-main))
