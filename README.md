jimbo-files
===========

## run it

start the webserver
```
AWS_S3_ACCESS_KEY="some-key" AWS_S3_SECRET_KEY="some-secret" AWS_S3_BUCKET="some-bucket" lein ring server
```

make sure you have the following image in your s3 bucket: 20557/image/5848694259

generate a jwt
```
jimbo-files.core=> (-> {:w 550, :i 5848694259, :u 20557, :t 2, :h 200, :c "image/jpeg"} jwt (sign :HS256 "secret") to-str)
"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjIjoiaW1hZ2VcL2pwZWciLCJpIjo1ODQ4Njk0MjU5LCJoIjoyMDAsInciOjU1MCwidCI6MiwidSI6MjA1NTd9.3qW52hH3VfQI6zuSkCYIz0NKFkhJ5WETKW4_Z5tdc0M"
```

get the image (jwt without the header part)
```
curl -v http://localhost:3000/images/eyJpdyI6NTUwLCJpaWQiOjU4NDg2OTQyNTksIndpZCI6MjA1NTcsInR5IjoyLCJpaCI6MjAwLCJpY3QiOiJpbWFnZVwvanBlZyJ9.La07_Qb_cUC3Hp_mtPFgc985efyrDN7JChXGc5oAQoU
```

## todos

 * animated gif support
 * image filter correct?
