curl -k "https://t0.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=$1&size=32"  -o .camunda/favicon.png

echo "{\"contents\": \"data:image/png;base64,$(cat .camunda/favicon.png | base64 | tr -d '\r\n')\"}"