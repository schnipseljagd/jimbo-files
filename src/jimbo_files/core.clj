(ns jimbo-files.core
  (:require [amazonica.aws.s3 :as s3]
            [clojure.java.io :as io]
            [image-resizer.core :refer :all]
            [image-resizer.format :as format]
            [image-resizer.util :as util]
            [cheshire.core :as json])
  (:import [java.io File ByteArrayOutputStream ByteArrayInputStream]
           [javax.imageio ImageIO]))

(def s3-cred {:access-key (get (System/getenv) "AWS_S3_ACCESS_KEY")
              :secret-key (get (System/getenv) "AWS_S3_SECRET_KEY")})

(def s3-bucket (get (System/getenv) "AWS_S3_BUCKET"))

(defn s3-get-object [path]
  (s3/get-object s3-cred s3-bucket path))

(defn s3-put-object-metadata [path metadata]
  (s3/copy-object
    s3-cred
    :source-bucket-name s3-bucket
    :destination-bucket-name s3-bucket
    :source-key path
    :destination-key path
    :new-object-metadata metadata))

(defn s3-image-path [website-id image-id]
  (format "%s/image/%s" website-id image-id))

(defn image-resizer-format-as-stream-for-mime-type [buffered-image mime-type]
  (let [baos (ByteArrayOutputStream.)]
    (let [writer (.next (ImageIO/getImageWritersByMIMEType mime-type))]
        (.setOutput writer (ImageIO/createImageOutputStream baos))
        (.write writer buffered-image)
        (ByteArrayInputStream. (.toByteArray baos)))))

(defn get-jimbo-image-as-stream [website-id image-id]
  (let [object (s3-get-object(s3-image-path website-id image-id))]
    (image-resizer-format-as-stream-for-mime-type (util/buffered-image (:object-content object)) (:content-type (:object-metadata object)))))

(defn get-image-profile-data-from-s3-metadata [type metadata]
  ((keyword type) (json/parse-string (:jimdo-profiles (:user-metadata metadata)) true)))

(defn resize-jimbo-image-as-stream [website-id image-id type]
  (let [object (s3-get-object(s3-image-path website-id image-id))]
    (let [image-profile (get-image-profile-data-from-s3-metadata type (:object-metadata object))]
      (image-resizer-format-as-stream-for-mime-type (resize (:object-content object) (:width image-profile) (:height image-profile)) (:content-type (:object-metadata object))))))

