(ns com.example.layout
  (:require
   [hiccup
    [page :refer [html5 include-js include-css]]]))

(defn index-page []
  (html5
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:http-equiv "X-UA-Compatible"
             :content    "IE=edge"}]
     [:meta {:name    "viewport"
             :content "width=device-width, initial-scale=1"}]
     (include-css "/css/compiled.css")]
    [:body
     ;; {:class "bg-white"}
     [:div {:id "app"}]
     (include-js "/js/compiled/main.js")]))
