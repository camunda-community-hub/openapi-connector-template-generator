# Set OS to "windows" or "unix" for the proper classpath separator
os="unix"

cd generators/connectors-codegen
mvn package -DskipTests
cd -
rm -rf out

[[ $os = "unix" ]] && separator=":" || separator=";"

java -DdebugOpenAPI -cp generators/connectors-codegen/target/connectors-codegen-openapi-generator-1.0.0.jar${separator}openapi-generator-cli.jar \
     org.openapitools.codegen.OpenAPIGenerator generate -g connectors-codegen -i in.yaml -o out
