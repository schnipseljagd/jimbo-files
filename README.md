jimbo-files
===========

start the webserver
```
AWS_S3_ACCESS_KEY="some-key" AWS_S3_SECRET_KEY="some-secret" AWS_S3_BUCKET="some-bucket" lein ring server
```

make sure you have the following image in your s3 bucket: 20557/image/5848694259
set the profiles and content-type
```
curl -v -XPOST -H'Content-type: application/json' -H'Accept: application/json' -d'{"profiles":{"std":{"width":271,"height":1500,"crop":false},"mobileapppreview":{"width":960,"height":640,"crop":false},"mobileappthumb":{"width":30,"height":30,"crop":true}},"content-type":"image/jpeg"}' http://localhost:3000/websites/20557/images/5848694259
```

get the original image
```
curl -v http://localhost:3000/websites/20557/images/5848694259
```

get the image for a profile
```
curl -v http://localhost:3000/websites/20557/images/5848694259/std
```
