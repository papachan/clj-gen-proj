(ns user
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.java.javadoc :refer (javadoc)]
            [clojure.string :as str]
            [clojure.pprint :refer (pprint)]
            [clojure.repl :refer (apropos dir doc find-doc pst source)]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :as tools-ns]
            [integrant.core :as ig]
            [integrant.repl :as ig-repl]
            [com.something.system :as system-util]))


(tools-ns/set-refresh-dirs "src")

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
  (tools-ns/refresh-all)

  ,)
