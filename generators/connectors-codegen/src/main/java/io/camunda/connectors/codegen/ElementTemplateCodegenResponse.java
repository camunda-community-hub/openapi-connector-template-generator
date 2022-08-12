package io.camunda.connectors.codegen;

import org.openapitools.codegen.*;

import java.util.*;

public class ElementTemplateCodegenResponse extends CodegenResponse {
    public boolean is200;

    public ElementTemplateCodegenResponse(final CodegenResponse response) {
        super();

        super.headers.addAll(response.headers);
        super.setResponseHeaders(response.getResponseHeaders());
        super.code = response.code;
        super.is1xx = response.is1xx;
        super.is2xx = response.is2xx;
        super.is3xx = response.is3xx;
        super.is4xx = response.is4xx;
        super.is5xx = response.is5xx;
        super.message = response.message;
        super.examples = response.examples;
        super.dataType = response.dataType;
        super.baseType = response.baseType;
        super.containerType = response.containerType;
        super.hasHeaders = response.hasHeaders;
        super.isString = response.isString;
        super.isNumeric = response.isNumeric;
        super.isInteger = response.isInteger;
        super.isShort = response.isShort;
        super.isLong = response.isLong;
        super.isUnboundedInteger = response.isUnboundedInteger;
        super.isNumber = response.isNumber;
        super.isFloat = response.isFloat;
        super.isDouble = response.isDouble;
        super.isDecimal = response.isDecimal;
        super.isByteArray = response.isByteArray;
        super.isBoolean = response.isBoolean;
        super.isDate = response.isDate;
        super.isDateTime = response.isDateTime;
        super.isUuid = response.isUuid;
        super.isEmail = response.isEmail;
        super.isModel = response.isModel;
        super.isFreeFormObject = response.isFreeFormObject;
        super.isAnyType = response.isAnyType;
        super.isDefault = response.isDefault;
        super.simpleType = response.simpleType;
        super.primitiveType = response.primitiveType;
        super.isMap = response.isMap;
        super.isArray = response.isArray;
        super.isBinary = response.isBinary;
        super.isFile = response.isFile;
        super.isNull = response.isNull;
        super.schema = response.schema;
        super.jsonSchema = response.jsonSchema;
        super.vendorExtensions = response.vendorExtensions;
        super.setMaxProperties(response.getMaxProperties());
        super.setMinProperties(response.getMinProperties());
        super.setUniqueItems(response.getUniqueItems());
        super.setMaxItems(response.getMaxItems());
        super.setMinItems(response.getMinItems());
        super.setMaxLength(response.getMaxLength());
        super.setMinLength(response.getMinLength());
        super.setExclusiveMinimum(response.getExclusiveMinimum());
        super.setExclusiveMinimum(response.getExclusiveMaximum());
        super.setMinimum(response.getMinimum());
        super.setMaximum(response.getMaximum());
        super.pattern = response.pattern;
        super.multipleOf = response.multipleOf;
        super.items = response.items;
        super.additionalProperties = response.additionalProperties;
        super.vars = response.vars;
        super.requiredVars = response.requiredVars;
        super.setHasValidation(response.getHasValidation());
        super.setAdditionalPropertiesIsAnyType(response.getAdditionalPropertiesIsAnyType());
        super.setHasVars(response.getHasVars());
        super.setHasRequired(response.getHasRequired());
        super.setHasDiscriminatorWithNonEmptyMapping(response.getHasDiscriminatorWithNonEmptyMapping());
        super.setComposedSchemas(response.getComposedSchemas());
        super.setHasMultipleTypes(response.getHasMultipleTypes());
        super.setContent(response.getContent());

        this.is200 = response.code.equals("200");
    }
}
