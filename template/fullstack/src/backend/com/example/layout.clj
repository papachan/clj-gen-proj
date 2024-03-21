(ns com.example.layout
  (:require
   [hiccup.core :as h]
   [hiccup.page :as hp]))

(defn homepage []
  (h/html
   (hp/html5
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:http-equiv "X-UA-Compatible"
             :content "IE=edge"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (hp/include-css "/css/compiled.css")
     [:body
      ;; {:class "bg-white"}
      [:div {:id "app"}]
      (hp/include-js "/js/compiled/main.js")]])))
