(defproject jimbo-files "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-json "0.2.0"]
                 [amazonica "0.1.15"]
                 [image-resizer "0.1.5"]
                 [cheshire "5.2.0"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler jimbo-files.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
