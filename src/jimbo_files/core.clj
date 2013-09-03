(ns jimbo-files.core)

(require '[aws.sdk.s3 :as s3],
         '[clojure.java.io :as io],
         '[image-resizer.core :refer :all],
         '[image-resizer.format :as format])

(def s3-cred {:access-key "AKIAIUCPDYCDBFXFMNTA",
              :secret-key "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"})

(defn print-meta [o]
  println (:metadata o))

(defn copy-s3-object [output]
  (io/copy (:content (s3/get-object s3-cred "jimbo-files" "simple.jpg"))
           output))

(defn resize-and-save [input]
  (format/as-file (resize input 10 10)
                  "/tmp/simple_thumb.jpg"))

(print-meta (s3/get-object s3-cred "jimbo-files" "simple.jpg"))

(copy-s3-object (io/output-stream "simple.jpg"))

(resize-and-save (io/file "simple.jpg"))

(defn resize-jimbo-image [filename]
  (with-open [input (:content (s3/get-object s3-cred "jimbo-files" filename))]
    (resize input 10 10)))

(format/as-file (resize-jimbo-image "simple.jpg")
                "/tmp/simple_thumb.jpg")
