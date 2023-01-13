package io.camunda.connectors.codegen;

import java.util.regex.Pattern;

import org.apache.commons.text.CaseUtils;
import org.openapitools.codegen.CodegenOperation;

public class ElementTemplateCodegenOperation extends CodegenOperation {
  protected boolean isGETorDELETE;
  protected String sanitizedOperationId;
  protected String camelCaseReturnType;

  public ElementTemplateCodegenOperation(CodegenOperation o) {
    super();

    this.responseHeaders.addAll(o.responseHeaders);
    this.hasAuthMethods = o.hasAuthMethods;
    this.hasConsumes = o.hasConsumes;
    this.hasProduces = o.hasProduces;
    this.hasParams = o.hasParams;
    this.hasOptionalParams = o.hasOptionalParams;
    this.returnTypeIsPrimitive = o.returnTypeIsPrimitive;
    this.returnSimpleType = o.returnSimpleType;
    this.subresourceOperation = o.subresourceOperation;
    this.isMap = o.isMap;
    this.isArray = o.isArray;
    this.isMultipart = o.isMultipart;
    this.isResponseBinary = o.isResponseBinary;
    this.hasReference = o.hasReference;
    this.isRestfulIndex = o.isRestfulIndex;
    this.isRestfulShow = o.isRestfulShow;
    this.isRestfulCreate = o.isRestfulCreate;
    this.isRestfulUpdate = o.isRestfulUpdate;
    this.isRestfulDestroy = o.isRestfulDestroy;
    this.isRestful = o.isRestful;
    this.path = o.path;
    this.operationId = o.operationId;
    this.returnType = o.returnType;
    this.httpMethod = o.httpMethod;
    this.returnBaseType = o.returnBaseType;
    this.returnContainer = o.returnContainer;
    this.summary = o.summary;
    this.unescapedNotes = o.unescapedNotes;
    this.notes = o.notes;
    this.baseName = o.baseName;
    this.defaultResponse = o.defaultResponse;
    this.discriminator = o.discriminator;
    this.consumes = o.consumes;
    this.produces = o.produces;
    this.bodyParam = o.bodyParam;
    this.allParams = o.allParams;
    this.bodyParams = o.bodyParams;
    this.pathParams = o.pathParams;
    this.queryParams = o.queryParams;
    this.headerParams = o.headerParams;
    this.formParams = o.formParams;
    this.authMethods = o.authMethods;
    this.tags = o.tags;
    this.responses = o.responses;
    this.imports = o.imports;
    this.examples = o.examples;
    this.externalDocs = o.externalDocs;
    this.vendorExtensions = o.vendorExtensions;
    this.nickname = o.nickname;
    this.operationIdLowerCase = o.operationIdLowerCase;
    this.operationIdCamelCase = o.operationIdCamelCase;

    this.isGETorDELETE = o.httpMethod.equals("GET") || o.httpMethod.equals("DELETE");
    this.sanitizedOperationId = o.operationId.replace("/", "_");
    // Required so that "BaseType-Trial" turns into "BaseTypeTrial" and not "BasetypeTrial"
    String tmp = o.returnBaseType;
    if (tmp != null) {
      Pattern p = Pattern.compile("[A-Z]");
      tmp = p.matcher(tmp).replaceAll(m -> "-" + m.group().toLowerCase());
    }
    this.camelCaseReturnType = CaseUtils.toCamelCase(tmp, true, '-', '_');
    System.out.println("From: " + tmp + " To: " + this.camelCaseReturnType);
  }
}
