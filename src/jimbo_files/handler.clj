(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [clj-jwt.core :refer :all]
            [image-resizer.core :refer :all]
            [image-resizer.util :as util]
            [image-resizer.format :as format]))

(defn token->image-data [token]
  (let [claims (:claims (str->jwt token))]
    {:website-id (:wid claims) :image-id (:iid claims) :width (:iw claims) :height (:ih claims) :type (:ty claims) :content-type (:ict claims)}))

(defn get-image-as-stream [path resize-algorithm image-data]
  (let [object (s3-get-object path)]
    (format/as-stream-by-mime-type (resize-algorithm (:object-content object) (:width image-data) (:height image-data)) (:content-type image-data))))

(defn no-resize [input width height]
  (util/buffered-image input))

(defn get-image-resize-algorithm [image-data]
  (case (:type image-data)
    1 resize
    2 resize-and-crop
    no-resize))

(defn get-image-as-stream-by-token [token]
  (let [image-data (token->image-data token)]
    (get-image-as-stream (s3-image-path (:website-id image-data) (:image-id image-data)) (get-image-resize-algorithm image-data) image-data)))

(defroutes app-routes
  (GET "/images/:token" [token]
    (get-image-as-stream-by-token token))
  (route/not-found "Image not found"))

(def app
  (-> (handler/api app-routes)
  (middleware/wrap-json-body)
  (middleware/wrap-json-response)
  (middleware/wrap-json-params)))
