(ns com.example.handler-test
  (:require
   [clojure.data.json :as json]
   [clojure.test :refer [deftest is testing]]
   [com.example.handler :refer [app]]))

(deftest login-route-test
  (testing "GET /api/v1/users/login returns 200"
    (let [response (app {:request-method :get
                         :uri            "/api/v1/users/login"})]
      (is (= 200 (:status response)))))

  (testing "POST /api/v1/users/login returns 200"
    (let [response (app {:request-method :post
                         :uri            "/api/v1/users/login"})]
      (is (= 200 (:status response))))))

(deftest not-found-test
  (testing "unknown routes return a JSON 404"
    (let [response (app {:request-method :get
                         :uri            "/does-not-exist"})]
      (is (= 404 (:status response)))
      (is (= {"error" true} (json/read-str (:body response)))))))
