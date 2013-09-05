jimbo-files
===========

```
AWS_S3_ACCESS_KEY="some-key" AWS_S3_SECRET_KEY="some-secret" AWS_S3_BUCKET="some-bucket" lein ring server
```

```
curl -v http://localhost:3000/websites/20557/images/5848694259
```
```
curl -v -XPOST -H'Content-type: application/json' -H'Accept: application/json' -d'{"profiles":{"std":{"width":271,"height":1500,"crop":false},"mobileapppreview":{"width":960,"height":640,"crop":false},"mobileappthumb":{"width":30,"height":30,"crop":true}},"content-type":"image/jpeg"}' http://localhost:3000/websites/20557/images/5848694259
```
