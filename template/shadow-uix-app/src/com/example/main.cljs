(ns com.example.main
  (:require
   [uix.dom]
   [uix.core :as uix :refer [defui $]]))

(defui app []
  ($ :.flex.flex-col.justify-center.items-center.h-screen
     ($ :h1.text-4xl.mb-4.font-bold
        "Hello Clojurescript")
     ($ :h2.text-2xl.font-semibold.text-blue-500
        "Something here")))

(defonce root (uix.dom/create-root (js/document.getElementById "root")))

(defn ^:export init []
  (uix.dom/render-root
   ($ uix/strict-mode
      ($ app))
   root))
