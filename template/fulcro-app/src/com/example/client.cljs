(ns com.example.client
  (:require
   [com.example.application :refer [SPA]]
   [com.fulcrologic.fulcro.react.version18 :refer [with-react18]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.dom :as dom]
   [fulcro.inspect.tool]))

(defonce app (-> (app/fulcro-app)
                 (with-react18)))

(fulcro.inspect.tool/add-fulcro-inspect! app)

(defsc Root [this props]
  (dom/div :.d-flex.align-items-center.justify-content-center.vh-100
    (dom/div :.text-center
      (dom/h3 "Hello Fulcro.")
        (dom/p "I have "
          (dom/strong "bold")
          " and "
          (dom/span :.text-danger "red")
          " text."))))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (reset! SPA app)
  (app/mount! app Root "app")
  (js/console.log "Loaded"))

(defn ^:export refresh
  "During development, shadow-cljs will call this on every hot reload of source. See shadow-cljs.edn"
  []
  ;; re-mounting will cause forced UI refresh, update internals, etc.
  (app/mount! app Root "app")
  ;; As of Fulcro 3.3.0, this addition will help with stale queries when using dynamic routing:
  (comp/refresh-dynamic-queries! app)
  (js/console.log "Hot reload"))
