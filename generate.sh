# Set OS to "windows" or "unix" for the proper classpath separator
os="unix"

if [ -z $1 ]; then
     cd generators/connectors-codegen
     mvn package -DskipTests
     cd -
fi

rm -rf .camunda

[[ $os = "unix" ]] && separator=":" || separator=";"

java -DdebugModels -cp generators/connectors-codegen/target/connectors-codegen-openapi-generator-1.0.0.jar${separator}openapi-generator-cli.jar \
     org.openapitools.codegen.OpenAPIGenerator generate -g connectors-codegen -i in.yaml -o .camunda \
     -t generators/connectors-codegen/src/main/resources/templates

for APIFILE in .camunda/element-templates/*.json; do    
    chmod 755 $APIFILE
    URL=$(jq -r '.[0].icon.faviconBasePath' $APIFILE)

    echo "======================"
    echo "=======START========="
    echo $URL

    BASE64=$(source ./fetch-favicon.sh $URL)

    echo $BASE64
    echo "=======END=========="
    echo "======================"

    # TODO preserve whitespaces
    cat <<< $(jq -r '.[0].icon = "BASE64"' $APIFILE) > $APIFILE
    sed -i '' "s@\"BASE64\"@$BASE64@g" $APIFILE
done


for FILE in .camunda/includes/*.feel; do
     FILENAME="${FILE##*/}"
     REPLACE=\<${FILENAME%.*}\>
     CONTENTS=`cat $FILE`

     for APIFILE in .camunda/element-templates/*.json; do
          if [ $os = "unix" ]; then
            sed -i '' "s@$REPLACE@$CONTENTS@g" $APIFILE
          else
            sed -i "s@$REPLACE@$CONTENTS@g" $APIFILE
          fi
     done
done