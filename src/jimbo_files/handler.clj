(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [cheshire.core :as json]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET ["/websites/:website-id/images/:image-id" :website-id #"[0-9]+" :image-id #"[0-9]+"]
       [website-id image-id]
    (get-jimbo-image-as-stream website-id image-id))
  (GET ["/websites/:website-id/images/:image-id/:profile" :website-id #"[0-9]+" :image-id #"[0-9]+"]
       [website-id image-id profile]
    (resize-jimbo-image-as-stream website-id image-id profile))
  (POST ["/websites/:website-id/images/:image-id" :website-id #"[0-9]+" :image-id #"[0-9]+"]
        [website-id image-id profiles content-type]
    (s3-put-object-metadata
      (s3-image-path website-id image-id)
      {:user-metadata {:jimdo-profiles (json/generate-string profiles)} :content-type content-type}))
  (route/not-found "Image not found"))

(def app
  (-> (handler/api app-routes)
  (middleware/wrap-json-body)
  (middleware/wrap-json-response)
  (middleware/wrap-json-params)))
