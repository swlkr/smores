# smores

Yet another clojure instagram client library

## Usage

```bash
lein plz smores
```

or

```clojure
; add the following to :dependencies in your project.clj
[smores "1.0.0"]
```

```clojure
(ns your-project.core
  (:require [smores.core :as ig]
            [smores.auth :as auth]))

(ig/set-creds! {:client-id ""
                :client-secret ""
                :redirect-uri ""})

; auth

; get authentication url for redirecting users back to your app
(auth/url)
; => https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code

(auth/url {:state "" :scope "basic+public_content"})
; => https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code&scope=basic+public_content

; using something like compojure for the redirect
(defn access-token-handler [{:keys [params error]}]
  (let [_ (auth/set-access-token! (:code params))]
    (ok {})))

(GET "/redirect-uri" [] access-token-handler)
```
