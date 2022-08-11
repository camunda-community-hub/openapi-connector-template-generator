# Set OS to "windows" or "unix" for the proper classpath separator
os="unix"

if [ -z $1 ]; then
     cd generators/connectors-codegen
     mvn package -DskipTests
     cd -
fi

rm -rf .camunda/element-templates

[[ $os = "unix" ]] && separator=":" || separator=";"

java -DdebugModels -cp generators/connectors-codegen/target/connectors-codegen-openapi-generator-1.0.0.jar${separator}openapi-generator-cli.jar \
     org.openapitools.codegen.OpenAPIGenerator generate -g connectors-codegen -i in.yaml -o .camunda/element-templates \
     -t generators/connectors-codegen/src/main/resources/connectors-codegen

for FILE in .camunda/element-templates/src/*.feel; do
     FILENAME="${FILE##*/}"
     REPLACE=\<${FILENAME%.*}\>
     CONTENTS=`cat $FILE`

     for APIFILE in .camunda/element-templates/*.json; do
          sed -i '' "s@$REPLACE@$CONTENTS@g" $APIFILE
     done
done