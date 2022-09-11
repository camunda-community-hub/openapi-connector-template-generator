package io.camunda.connectors.codegen;

import org.junit.Test;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

public class ConnectorsCodegenGeneratorTest {

  @Test
  public void launchCodeGenerator() {
    final CodegenConfigurator configurator = new CodegenConfigurator()
              .setGeneratorName("connectors-codegen")
              .setInputSpec("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/2_0/petstore.yaml")
              .setOutputDir("out/connectors-codegen");

    final ClientOptInput clientOptInput = configurator.toClientOptInput();

    DefaultGenerator generator = new DefaultGenerator();
    generator.opts(clientOptInput).generate();
  }
}