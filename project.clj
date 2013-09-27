(defproject jimbo-files "0.1.0-SNAPSHOT"
  :description "image resizing geloet"
  :url "https://github.com/schnipseljagd/jimbo-files"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-json "0.2.0"]
                 [clj-aws-s3 "0.3.7"]
                 [image-resizer "0.1.6"]
                 [metrics-clojure-ring "1.0.1"]
                 [clj-jwt "0.0.3"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler jimbo-files.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}}
  :uberjar-name "jimbo-files.jar"
  :min-lein-version "2.3.2")
