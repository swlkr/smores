(ns smores.core)

(defonce creds (atom {:client-id nil
                      :client-secret nil
                      :redirect-uri nil
                      :user nil
                      :access-token nil}))

(defn set-creds! [{:keys [client-id client-secret redirect-uri user access-token]}]
  (reset! creds {:client-id client-id
                 :client-secret client-secret
                 :redirect-uri redirect-uri
                 :user user
                 :access-token access-token}))

(defn get-creds []
  (deref creds))

