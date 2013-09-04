(ns jimbo-files.core)

(require '[aws.sdk.s3 :as s3]
         '[clojure.java.io :as io]
         '[image-resizer.core :refer :all]
         '[image-resizer.format :as format]
         '[image-resizer.util :as util])

(def s3-cred {:access-key (get (System/getenv) "AWS_S3_ACCESS_KEY")
              :secret-key (get (System/getenv) "AWS_S3_SECRET_KEY")})

(def s3-bucket (get (System/getenv) "AWS_S3_BUCKET"))

(defn print-s3-meta [path]
  println (:metadata (s3/get-object s3-cred s3-bucket path)))

(defn copy-s3-object [output, path]
  (io/copy (:content (s3/get-object s3-cred s3-bucket path))
           output))

(defn s3-get-object-content [path]
  (:content (s3/get-object s3-cred s3-bucket path)))

(defn resize-jimbo-image [path width height]
  (with-open [input (s3-get-object-content path)]
    (resize input width height)))

(defn s3-image-path [website-id image-id]
  (format "%s/image/%s" website-id image-id))

(defn jimbo-image-as-stream [website-id image-id]
  (format/as-stream (util/buffered-image (s3-get-object-content (s3-image-path website-id image-id)) ) "jpg"))

(defn resize-jimbo-image-as-stream [website-id image-id type]
  (format/as-stream (resize-jimbo-image (s3-image-path website-id image-id) 40 60) "jpg"))

