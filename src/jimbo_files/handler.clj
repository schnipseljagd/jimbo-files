(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        [image-resizer.format :as format])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn resize-jimbo-image-as-stream [filename]
  (format/as-stream (resize-jimbo-image filename) "jpg"))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/websites/:website-id/images/:image-id" [website-id image-id]
    (resize-jimbo-image-as-stream (format "%s/image/%s" (biginteger website-id) (biginteger image-id))))
  (route/not-found "Image not found"))

(def app
  (handler/site app-routes))
