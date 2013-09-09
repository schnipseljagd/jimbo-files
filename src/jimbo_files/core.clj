(ns jimbo-files.core
  (:require [amazonica.aws.s3 :as s3]))

(def s3-cred {:access-key (get (System/getenv) "AWS_S3_ACCESS_KEY")
              :secret-key (get (System/getenv) "AWS_S3_SECRET_KEY")})

(def s3-bucket (get (System/getenv) "AWS_S3_BUCKET"))

(defn s3-get-object [path]
  (s3/get-object s3-cred s3-bucket path))

(defn s3-image-path [website-id image-id]
  (format "%s/image/%s" website-id image-id))
