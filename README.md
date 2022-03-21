# caliban-akka-http-sample

reference: https://github.com/ghostdogpr/caliban/blob/master/examples/src/main/scala/example/akkahttp/ExampleApp.scala

## Request

```sh
curl -X POST \
http://localhost:8088/graphql \
-H 'Content-Type: application/json' \
-d '{
"query": "query { characters { name }}"
}'
```
