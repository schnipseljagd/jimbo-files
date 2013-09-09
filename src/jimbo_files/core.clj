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

(defn get-jimbo-image-as-stream [website-id image-id]
  (let [object (s3-get-object (s3-image-path website-id image-id))]
    (format/as-stream-by-mime-type (util/buffered-image (:object-content object)) (:content-type (:object-metadata object)))))

(defn get-image-profile-data-from-s3-metadata [profile metadata]
  ((keyword profile) (json/parse-string (:jimdo-profiles (:user-metadata metadata)) true)))

(defn image-resize-alg [crop]
  (cond
    (= crop true) resize-and-crop
    :else resize))

(defn resize-jimbo-image [image-profile input]
  ((image-resize-alg (:crop image-profile)) input (:width image-profile) (:height image-profile)))

(defn resize-jimbo-image-as-stream [website-id image-id profile]
  (let [object (s3-get-object (s3-image-path website-id image-id))]
      (format/as-stream-by-mime-type (resize-jimbo-image (get-image-profile-data-from-s3-metadata profile (:object-metadata object)) (:object-content object)) (:content-type (:object-metadata object)))))
