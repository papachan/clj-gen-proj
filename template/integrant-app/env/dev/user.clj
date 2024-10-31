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
            [integrant.core :as ig]
            [integrant.repl :as ig-repl]
            [com.something.system :as system-util]))


(r/set-refresh-dirs "src" "env/dev")

(defn- integrant-prep!
  []
  (ig-repl/set-prep! #(ig/prep system-util/config))
  (ig-repl/prep))

(defn reset
  "Restart system."
  []
  (ig-repl/reset))

(defn stop
  "Stop system."
  []
  (ig-repl/halt))

(defn start
  []
  (integrant-prep!)
  (ig-repl/init)
  (ig-repl/go))

(comment
  ;; Refresh changed namespaces
  (r/refresh-all)

  ,)
