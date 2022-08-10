rm -rf log
mkdir log

rm -rf generated/*

java -jar openapi-generator-cli-5.1.1.jar generate \
    -i openapi/main.yaml \
    -g javascript \
    -c config.json \
    -o generated/openapi-connector-templates \
    --additional-properties="foo=bar" 2>&1 | tee log/openapi-codegen-json.log
