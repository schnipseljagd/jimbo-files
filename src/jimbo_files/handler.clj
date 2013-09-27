(ns jimbo-files.handler
  (:use compojure.core
        jimbo-files.core
        ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]))

(defroutes app-routes
  (GET "/images/:token" [token]
    (get-image-as-stream-by-token token))
  (route/not-found "Image not found"))

(def app
  (-> (handler/api app-routes)
  (middleware/wrap-json-body)
  (middleware/wrap-json-response)
  (middleware/wrap-json-params)))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))
