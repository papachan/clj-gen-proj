(ns com.example.handler
  (:require
   [com.example.responses :refer [response]]
   [malli.util :as mu]
   [muuntaja.core :as m]
   [reitit.coercion.malli]
   [reitit.dev.pretty :as pretty]
   [reitit.openapi :as openapi]
   [reitit.ring :as ring]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [ring.middleware.cors :refer [wrap-cors]]))

(def app
  (ring/ring-handler
    (ring/router
      ["/"
       ["api-docs/swagger.json"
        {:get {:no-doc true
               :basePath "/"
               :swagger {:info {:title "com.example-api"
                                :description "swagger docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                                :version "0.0.1"}}
               :handler (swagger/create-swagger-handler)}}]
       ["api-docs/openapi.json"
        {:get {:no-doc true
               :basePath "/"
               :openapi {:info {:title "com.example-api"
                                :description "openapi3 docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                                :version "0.0.1"}}
               :handler (openapi/create-openapi-handler)}}]

       ["api"
        ["/v1"
         ["/users"
          {:tags #{"users endpoints"}}
          ["/login"
           {:post {:summary "login"
                   :responses {200 {:body [:map [:message :string]]}}
                   :handler (fn [_req]
                              (response 200 {:message :ok}))}}]]]]]
      {::default-options-endpoint nil
       :exception pretty/exception
       :data {:coercion   (reitit.coercion.malli/create
                            { ;; set of keys to include in error messages
                             :error-keys #{:type :coercion :in :schema :value :errors :humanized #_:transformed}
                             ;; support lite syntax
                             ;; :lite true
                             ;; schema identity function (default: close all map schemas)
                             :compile mu/closed-schema
                             ;; strip-extra-keys (effects only predefined transformers)
                             :strip-extra-keys true
                             ;; add/set default values
                             :default-values true
                             ;; malli options
                             :options nil})
              :muuntaja   m/instance
              :middleware [ ;; swagger & openapi
                           swagger/swagger-feature
                           openapi/openapi-feature
                           ;; query-params & form-params
                           parameters/parameters-middleware
                           ;; content-negotiation
                           muuntaja/format-negotiate-middleware
                           ;; encoding response body
                           muuntaja/format-response-middleware
                           ;; exception handling
                           exception/exception-middleware
                           ;; decoding request body
                           muuntaja/format-request-middleware
                           coercion/coerce-request-middleware

                           [wrap-json-body {:keywords? true}]
                           wrap-json-response]}})
    (ring/routes
      (swagger-ui/create-swagger-ui-handler
        {:path "/api-docs"
         :url "/api-docs/swagger.json"
         :config {:validatorUrl nil
                  :urls [{:name "swagger", :url "swagger.json"}
                         {:name "openapi", :url "openapi.json"}]
                  :urls.primaryName "swagger"
                  :operationsSorter "alpha"}})
      (ring/create-default-handler
        {:not-found (constantly {:status 404})}))
    {:middleware [[wrap-cors :access-control-allow-origin [#".*"]
                             :access-control-allow-methods [:get :put :post :delete]]]}))
