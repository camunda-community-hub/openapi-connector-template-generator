favurl=$1
favout=$2

curl -k "https://t0.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=${favurl}&size=32"  -o ${favout}/favicon.png

echo "{\"contents\": \"data:image/png;base64,$(cat ${favout}/favicon.png | base64 | tr -d '\r\n')\"}"