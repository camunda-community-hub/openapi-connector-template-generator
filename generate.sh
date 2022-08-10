cd generators/connectors-codegen
mvn package -DskipTests
cd -

java -cp generators/connectors-codegen/target/connectors-codegen-openapi-generator-1.0.0.jar:openapi-generator-cli.jar \
     org.openapitools.codegen.OpenAPIGenerator generate -g connectors-codegen -i in.yaml -o out