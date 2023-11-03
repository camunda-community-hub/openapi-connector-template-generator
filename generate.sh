#!/usr/bin/env bash
# Set OS to "windows" or "unix" for the proper classpath separator
os="unix"
set -e
GREEN='\033[0;32m'
NC='\033[0m' # No Color
B=$(tput bold)
N=$(tput sgr0)
BGREEN=$GREEN$B
NNC=$NC$N

while getopts i:o:p:a:vfh flag; do
    case "${flag}" in
    i) in=${OPTARG} ;;
    o) out=${OPTARG} ;;
    p) prefix=${OPTARG} ;;
    v) verbose=true ;;
    f) fast=t ;;
    h) help=t ;;
    esac
done

if [ "$help" = "t" ]; then
    echo "Camunda OpenAPI Connector template generator manual\n"
    echo "Option \t Meaning"
    echo "-i \t Path to the input schema."
    echo "-o \t Output dir for the generated template files"
    echo "-p \t Prefix for output file names [optional]"
    echo "-v \t If flag set, output is verbose [optional]"
    echo "-f \t Fast option; skips compiling the generator java code again. Use when no changes made to the generator or templates [optional]"
    echo "-h \t This help [optional]"
    exit 1
fi

if [ "$in" = "" ]; then
    if [[ $1 = "" ]]; then
        in=examples/openapi/petstore.yaml
    else
        if [[ $1 != -* ]]; then
            in=$1
        else
            in=examples/openapi/petstore.yaml
        fi
    fi
fi

if [ "$out" = "" ]; then
    out=examples/bpmn/.camunda
fi

if [ "$verbose" != "true" ]; then
    verbose="false"
fi

openapi_global="foo=bar"

if [ "$verbose" = "true" ]; then
    openapi_global="debugOpenAPI=true"
fi

if [ "$fast" != "t" ]; then
    cd generators/connectors-codegen
    mvn package -DskipTests
    cd -
    cd postprocessor
    mvn package -DskipTests
    cd -
fi

rm -rf $out

[[ $os = "unix" ]] && separator=":" || separator=";"

java -cp generators/connectors-codegen/target/connectors-codegen-openapi-generator-1.0.0.jar${separator}openapi-generator-cli.jar \
    org.openapitools.codegen.OpenAPIGenerator generate -g connectors-codegen -i $in -o $out \
    -t generators/connectors-codegen/src/main/resources/templates --global-property $openapi_global

# replace bad markdown chars in parameter descriptions that are misinterpreted as escaped control chars
for MODELFILE in $out/includes/*.feel; do
    chmod 755 $MODELFILE
    if [ $os = "unix" ]; then
        sed -i.bak "s@\*@@g" $MODELFILE && rm $MODELFILE.bak # https://stackoverflow.com/questions/5694228/sed-in-place-flag-that-works-both-on-mac-bsd-and-linux
    else
        sed -i "s@\*@@g" $MODELFILE
    fi
done

# add favicon, replace null default values
for APIFILE in $out/element-templates/*.json; do
    chmod 755 $APIFILE
    URL=$(jq -r '.icon.faviconBasePath' $APIFILE)

    source ./fetch-favicon.sh $URL $out

    BASE64=$(source ./fetch-favicon.sh $URL $out)

    cat <<<$(jq -r '.icon = "BASE64"' $APIFILE) >$APIFILE

    if [ $os = "unix" ]; then
        sed -i.bak "s@\"BASE64\"@$BASE64@g" $APIFILE && rm $APIFILE.bak
        sed -i.bak 's@"value": "null",@@g' $APIFILE && rm $APIFILE.bak
    else
        sed -i "s@\"BASE64\"@$BASE64@g" $APIFILE
        sed -i 's@"value": "null",@@g' $APIFILE
    fi

    # beautify
    cat $APIFILE | jq '.' >tmp
    mv tmp $APIFILE
done

java -jar postprocessor/target/postprocessor-jar-with-dependencies.jar $out

# append an optional prefix to the file name
if [ "$prefix" != "" ]; then
    echo "adding prefix"
    for APIFILE in $out/element-templates/*.json; do
        BASEPATH="${APIFILE%/*}"
        FILENAME="${APIFILE##*/}"

        if [ $os = "unix" ]; then
            sed -i.bak "s@<PREFIX>@${prefix} - @g" $APIFILE && rm $APIFILE.bak
        else
            sed -i "s@<PREFIX>@${prefix} - @g" $APIFILE
        fi

        mv $APIFILE ${BASEPATH}/${prefix}_${FILENAME}
    done
fi

echo "------------------------------------------------------------------------"
echo -e "${BGREEN}GENERATOR SUCCESS${NNC}"
echo -e "Generated connector templates for schema ${GREEN}$in${NC} can be found in ${GREEN}$out${NC}"
echo "------------------------------------------------------------------------"
