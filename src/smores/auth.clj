(ns smores.auth
  (:require [smores.core :refer [get-creds creds]]
            [smores.http :as http]))

(defonce auth-url "https://api.instagram.com/oauth/")

(defn get-access-token-req [code]
  (let [{:keys [client-id client-secret redirect-uri]} (get-creds)
        url (str auth-url "access-token")]
    {:url url
     :method :post
     :form-params {"client_id" client-id
                   "client_secret" client-secret
                   "grant_type" "authorization_code"
                   "redirect_uri" redirect-uri
                   "code" code}}))

(defn get-access-token [code]
  (-> code
      get-access-token-req
      http/send!))

(defn set-access-token! [code]
  (let [{:keys [access-token user]} (get-access-token code)]
    (swap! creds assoc :access-token access-token :user user)))

(defn url [{:keys [response-type state scope]}]
  (let [{:keys [client-id redirect-uri]} (get-creds)]
    (str auth-url "authorize/?client_id=" client-id
         "&redirect_uri=" redirect-uri
         "&response_type=" (or response-type "code")
         "&state=" state
         "&scope=" (or scope "basic"))))
