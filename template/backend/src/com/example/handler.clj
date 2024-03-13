(ns com.example.handler
  (:require
   [com.example.middleware :as middleware]
   [muuntaja.core :as m]
   [reitit.dev.pretty :as pretty]
   [reitit.ring :as ring]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(def app
  (ring/ring-handler
   (ring/router
    [["/swagger.json"
      {:get {:no-doc true
             :basePath "/"
             :swagger {:info {:title "com.example"
                              :description "swagger docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                              :version "0.0.1"}}
             :handler (swagger/create-swagger-handler)}}]

     ["/api"
      ["/v1"
       ["/users"
        {:tags #{"users endpoints"}}
        ["/login"
         {:post {:summary "login"
                 :responses {200 {:content {:default {:description "Success"
                                                      :schema [:map [:success :boolean]]
                                                      :example {:success true}}}}}
                 :handler (fn [_req]
                            {:status 200
                             :body {:success true}})}}]]]]]

    {:exception pretty/exception
     :data {:muuntaja m/instance
            :middleware [;; query-params & form-params
                         parameters/parameters-middleware
                         ;; content-negotiation
                         muuntaja/format-negotiate-middleware
                         ;; encoding response body
                         muuntaja/format-response-middleware
                         ;; exception handling
                         exception/exception-middleware
                         ;; decoding request body
                         muuntaja/format-request-middleware
                         ;; multipart
                         multipart/multipart-middleware
                         ;; response json
                         [wrap-json-body {:keywords? true}]
                         wrap-json-response
                         ;; cors
                         ;; middleware/allow-cross-origin
                         ]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/"
      :config {:validatorUrl nil
               :urls [{:name "swagger", :url "swagger.json"}]
               :urls.primaryName "swagger"
               :operationsSorter "alpha"}})
    (ring/create-default-handler))))
