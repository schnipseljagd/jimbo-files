(ns jimbo-files.core)

(require '[aws.sdk.s3 :as s3],
         '[clojure.java.io :as io],
         '[image-resizer.core :refer :all],
         '[image-resizer.format :as format])

(def s3-cred {:access-key "AKIAIUCPDYCDBFXFMNTA",
              :secret-key "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"})

(defn print-s3-meta [bucket, path]
  println (:metadata (s3/get-object s3-cred bucket path)))

(defn copy-s3-object [output]
  (io/copy (:content (s3/get-object s3-cred "jimbo-files" "simple.jpg"))
           output))

(defn resize-jimbo-image [filename]
  (with-open [input (:content (s3/get-object s3-cred "jimbo-files" filename))]
    (resize input 40 40)))

