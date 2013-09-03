(defproject jimbo-files "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [clj-aws-s3 "0.3.6"]
                 [image-resizer "0.1.5"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler jimbo-files.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
