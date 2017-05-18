(ns smores.auth-test
  (:require [clojure.test :refer :all]
            [smores.auth :as auth]
            [smores.core :as ig]
            [environ.core :refer [env]]))

(deftest url
  (let [url-with-default-params "https://api.instagram.com/oauth/authorize/?client_id=&redirect_uri=&response_type=code&state=&scope=basic"
        url-with-overriden-state-and-scope-params "https://api.instagram.com/oauth/authorize/?client_id=&redirect_uri=&response_type=code&state=123&scope=basic+public_content"
        valid-url (str "https://api.instagram.com/oauth/authorize/?client_id=" (env :client-id) "&redirect_uri=" (env :redirect-uri) "&response_type=code&state=&scope=basic")
        _ (ig/set-creds! {:client-id nil
                          :redirect-uri nil})]

    (testing "should be a valid url with default params when called with an empty map"
      (is (= url-with-default-params
             (auth/url {}))))

    (testing "should be a valid url with default params when called with nil"
      (is (= url-with-default-params
             (auth/url nil))))

    (testing "should be a valid url with default params when called with a blank string"
      (is (= url-with-default-params
             (auth/url ""))))

    (testing "should be a valid url with state and scope params overridden"
      (is (= url-with-overriden-state-and-scope-params
             (auth/url {:state 123 :scope "basic+public_content"}))))

    (testing "should be a valid url"
      (let [_ (ig/set-creds! {:client-id (env :client-id)
                              :redirect-uri (env :redirect-uri)})]
        (is (= valid-url
               (auth/url {})))))))

(deftest make-access-token-request
  (testing "should append code to form-params"
    (let [req (auth/get-access-token-req "the code")]
      (is (= {:url (str auth/auth-url "access-token")
              :method :post
              :form-params {"client_id" ""
                            "client_secret" ""
                            "grant_type" "authorization_code"
                            "redirect_uri" ""
                            "code" "the code"}})))))
