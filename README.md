jimbo-files
===========

start the webserver
```
AWS_S3_ACCESS_KEY="some-key" AWS_S3_SECRET_KEY="some-secret" AWS_S3_BUCKET="some-bucket" lein ring server
```

make sure you have the following image in your s3 bucket: 20557/image/5848694259
generate a jwt
```
jimbo-files.handle=> (-> {:wid 20557, :iid 5848694259, :iw 550, :ih 200 :ty 2 :ict "image/jpeg"} jwt (sign :HS256 "secret") to-str)
"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpdyI6NTUwLCJpaWQiOjU4NDg2OTQyNTksIndpZCI6MjA1NTcsInR5IjoyLCJpaCI6MjAwLCJpY3QiOiJpbWFnZVwvanBlZyJ9.La07_Qb_cUC3Hp_mtPFgc985efyrDN7JChXGc5oAQoU"
```

get the image
```
curl -v http://localhost:3000/images/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpdyI6NTUwLCJpaWQiOjU4NDg2OTQyNTksIndpZCI6MjA1NTcsInR5IjoyLCJpaCI6MjAwLCJpY3QiOiJpbWFnZVwvanBlZyJ9.La07_Qb_cUC3Hp_mtPFgc985efyrDN7JChXGc5oAQoU
```
