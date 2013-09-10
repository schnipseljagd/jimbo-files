(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        ring.util.response
        [metrics.ring.expose :only (expose-metrics-as-json)]
        [metrics.ring.instrument :only (instrument)])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]))

(defroutes app-routes
  (GET "/images/:token" [token]
    (get-image-as-stream-by-token token))
  (route/not-found "Image not found"))

(def app
  (-> (handler/api app-routes)
  (expose-metrics-as-json)
  (instrument)
  (middleware/wrap-json-body)
  (middleware/wrap-json-response)
  (middleware/wrap-json-params)))
