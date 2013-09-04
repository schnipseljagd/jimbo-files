(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        [image-resizer.format :as format])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET ["/websites/:website-id/images/:image-id" :website-id #"[0-9]+" :image-id #"[0-9]+"]
       [website-id image-id]
    (jimbo-image-as-stream website-id image-id))
  (GET ["/websites/:website-id/images/:image-id/:type" :website-id #"[0-9]+" :image-id #"[0-9]+"]
       [website-id image-id type]
    (resize-jimbo-image-as-stream website-id image-id type))
  (route/not-found "Image not found"))

(def app
  (handler/site app-routes))
