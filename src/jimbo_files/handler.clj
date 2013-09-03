(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        [image-resizer.format :as format])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn resize-jimbo-image-as-stream [path type]
  (format/as-stream (resize-jimbo-image path 40 60) "jpg"))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET ["/websites/:website-id/images/:image-id" :website-id #"[0-9]+" :image-id #"[0-9]+"]
       [website-id image-id & params]
    (resize-jimbo-image-as-stream 
      (format "%s/image/%s" website-id image-id)
      (if (:type params) (:type params) "std")))
  (route/not-found "Image not found"))

(def app
  (handler/site app-routes))
