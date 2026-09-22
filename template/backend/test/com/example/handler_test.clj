(ns com.example.handler-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [io.github.handler :refer [app]]))

(deftest swagger-test
  (testing "GET /swagger.json returns 200"
    (let [response (app {:request-method :get
                         :uri            "/swagger.json"})]
      (is (= 200 (:status response))))))

(deftest login-route-test
  (testing "POST /api/v1/users/login returns 200"
    (let [response (app {:request-method :post
                         :uri            "/api/v1/users/login"})]
      (is (= 200 (:status response))))))

(deftest not-found-test
  (testing "unknown routes return 404"
    (let [response (app {:request-method :get
                         :uri            "/does-not-exist"})]
      (is (= 404 (:status response))))))
