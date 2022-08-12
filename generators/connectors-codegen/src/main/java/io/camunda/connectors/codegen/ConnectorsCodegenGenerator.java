package io.camunda.connectors.codegen;

import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.DefaultCodegen;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.OperationMap;
import org.openapitools.codegen.model.OperationsMap;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class ConnectorsCodegenGenerator extends DefaultCodegen implements CodegenConfig {

  private String apiFolder = "element-templates";
  private String modelFolder = "includes";

  public ConnectorsCodegenGenerator() {
    super();

    templateDir = "templates";
    outputFolder = "generated";

    modelTemplateFiles.put("model.mustache", ".feel");
    modelTemplateFiles.put("response.mustache", ".response");
    apiTemplateFiles.put("api.mustache", ".json");
  }

  public CodegenType getTag() {
    return CodegenType.OTHER;
  }

  public String getName() {
    return "connectors-codegen";
  }

  public String getHelp() {
    return "Generates element templates for use with Camunda Platform 8.";
  }

  @Override
  public String modelFileFolder() {
    return outputFolder + "/" + modelFolder + "/" + modelPackage().replace('.', File.separatorChar);
  }

  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + apiFolder;
  }

  /**
   * Modifies the operation data before the output is generated.
   */
  @Override
  public OperationsMap postProcessOperationsWithModels(OperationsMap mapOfOperations, List<ModelMap> allModels) {

    // For debugging: set a break point on the next line, then debug the JUnit test called "LaunchGeneratorInDebugger"
    OperationsMap operationsWithImports = super.postProcessOperationsWithModels(mapOfOperations, allModels);
    OperationMap operationsWithMetaInfos = operationsWithImports.getOperations();

    List<ElementTemplateCodegenOperation> modifiedOperations = new ArrayList<>();
    List<CodegenOperation> codegenOperations = operationsWithMetaInfos.getOperation();

    for(CodegenOperation operation : codegenOperations){
      setPathParamsInPath(operation);

      ElementTemplateCodegenOperation modifiedOperation = new ElementTemplateCodegenOperation(operation);
      modifiedOperations.add(modifiedOperation);
    }

    operationsWithMetaInfos.setOperation(modifiedOperations);

    return operationsWithImports;
  }

  private void setPathParamsInPath(CodegenOperation operation) {
    operation.pathParams.stream()
            .map(param -> new AbstractMap.SimpleEntry<>("{" + param.paramName + "}", param))
            .forEach(entry -> operation.path = operation.path.replace(entry.getKey(),
                    String.format("\\\" + string(objects.%s_%s) + \\\"/", operation.operationId, entry.getValue().paramName)));
  }

  /**
   * Escapes a reserved word as defined in the `reservedWords` array.
   * This logic is only called if a variable matches the reserved words.
   *
   * @return the escaped term
   */
  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;
  }

  /**
   * Escapes unsafe characters to avoid code injection.
   *
   * @param input String to be cleaned up
   * @return the string with unsafe characters removed or escaped
   */
  @Override
  public String escapeUnsafeCharacters(String input) {
    return input; //TODO: Escape unsafe characters
  }

  /**
   * Escape single and/or double quote to avoid code injection
   *
   * @param input String to be cleaned up
   * @return the string with quotation marks escaped
   */
  public String escapeQuotationMark(String input) {
    return input.replace("\"", "\\\"");
  }
}
