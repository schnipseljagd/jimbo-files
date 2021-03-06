(ns jimbo-files.core
  (:use [clojure.set :only [rename-keys]])
  (:require [aws.sdk.s3 :as s3]
            [clj-jwt.core :refer :all]
            [image-resizer.core :refer :all]
            [image-resizer.util :as util]
            [image-resizer.format :as format]))

(def s3-cred {:access-key (get (System/getenv) "AWS_S3_ACCESS_KEY")
              :secret-key (get (System/getenv) "AWS_S3_SECRET_KEY")})

(def s3-bucket (get (System/getenv) "AWS_S3_BUCKET"))

(def s3-get-object (partial s3/get-object s3-cred s3-bucket))

(defn s3-image-path [website-id image-id]
  (format "%s/image/%s" website-id image-id))

(def token-header-part
  "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9")

(defn token->image-data [token]
  (let [claims (:claims (str->jwt (str token-header-part "." token)))]
    (rename-keys claims {:u :website-id, :i :image-id, :w :width, :h :height, :t :type, :c :content-type})))

(defn get-image-as-stream [path resize-algorithm image-data]
  (let [object (s3-get-object path)]
    (format/as-stream-by-mime-type (resize-algorithm (:content object) (:width image-data) (:height image-data)) (:content-type image-data))))

(defn no-resize [input width height]
  (util/buffered-image input))

(defn get-image-resize-algorithm [image-type]
  (case image-type
    1 resize
    2 resize-and-crop
    no-resize))

(defn get-image-as-stream-by-token [token]
  (let [image-data (token->image-data token)]
    (get-image-as-stream (s3-image-path (:website-id image-data) (:image-id image-data)) (get-image-resize-algorithm (:type image-data)) image-data)))
