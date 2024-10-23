(ns com.example.handler
  (:require
   [clojure.data.json :as json]
   [malli.util :as mu]
   [muuntaja.core :as m]
   [reitit.dev.pretty :as pretty]
   [reitit.ring :as ring]
   [reitit.http.coercion :as coercion]
   [reitit.coercion.malli :as malli]
   [reitit.http.interceptors.exception :as exception]
   [reitit.http.interceptors.muuntaja :as muuntaja]
   [reitit.http.interceptors.parameters :as parameters]
   [reitit.interceptor.sieppari :as sieppari]
   [ring.middleware.cors :refer [wrap-cors]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defn response-ok
  [_req]
  {:status 200
   :body   {:message :ok}})

(defn successful
  [_req]
  {:status 200
   :body   {:success true}})

(def app
  (ring/ring-handler
   (ring/router
    [["/api"
      ["/v1"
       ["/users"
        ["/login"
         {:get  {:responses {200 {:body [:map [:message :string]]}}
                 :handler   #'response-ok}
          :post {:responses {200 {:body [:map [:success :boolean]]}}
                 :handler   #'successful}}]]]]]

    {:exception pretty/exception
     :data      {:coercion     (reitit.coercion.malli/create
                                { ;; set of keys to include in error messages
                                 :error-keys       #{:type :coercion :in :schema :value :errors :humanized}
                                 ;; support lite syntax
                                 ;; :lite true
                                 ;; schema identity function (default: close all map schemas)
                                 :compile          mu/closed-schema
                                 ;; strip-extra-keys (effects only predefined transformers)
                                 :strip-extra-keys true
                                 ;; add/set default values
                                 :default-values   true
                                 ;; malli options
                                 :options          nil})
                 :muuntaja     m/instance
                 :interceptors [(parameters/parameters-interceptor)
                                (muuntaja/format-negotiate-interceptor)
                                (muuntaja/format-response-interceptor)
                                (exception/exception-interceptor)
                                (muuntaja/format-request-interceptor)
                                (coercion/coerce-exceptions-interceptor)
                                (coercion/coerce-response-interceptor)
                                (coercion/coerce-request-interceptor)]
                 :middleware [[wrap-json-body {:keywords? true}]
                              wrap-json-response
                              ;; [wrap-cors
                              ;;  :access-control-allow-origin [#".*"]
                              ;;  :access-control-allow-credentials "true"
                              ;;  :access-control-allow-methods [:get :put :post :delete]]
                              ]}})
   (ring/routes
    (ring/create-default-handler
     {:not-found (constantly {:status  404
                              :headers {"Content-Type" "application/json"}
                              :body    (json/write-str {:error true})})})
    {:executor sieppari/executor})))
