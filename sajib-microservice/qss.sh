function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
 local result=$(eval $curlCmd)
 # local httpCode="${result:(-3)}
 RESPONSE="${result%???}" 
 $RESPONSE
}
assertCurl 200 "curl http://localhost:7020/product-composite/1 -s"

