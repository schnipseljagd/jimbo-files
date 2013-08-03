(ns jimbo-files.core)

(require '[aws.sdk.s3 :as s3])
(require '[clojure.java.io :as io])
(require '[image-resizer.core :refer :all])
(require '[image-resizer.format :as format])

(def cred {:access-key "AKIAIUCPDYCDBFXFMNTA", 
           :secret-key "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"})

(defn print-meta [o]
  println (:metadata o))

(print-meta (s3/get-object cred "jimbo-files" "simple.jpg"))

(io/copy (:content (s3/get-object cred "jimbo-files" "simple.jpg"))
         (io/output-stream "simple.jpg"))

(format/as-file (resize (io/file "simple.jpg") 10 10)
                                  "/tmp/simple_thumb.jpg")
