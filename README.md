# OpenAPI Generator for the Camunda HTTP/JSON Connector Runtime

This project is a simple, hacky implementation of an OpenAPI generator for HTTP/JSON Connector templates, written in Java and Shell. It allows you to rapidly create Connector templates for any REST API that provides an OpenAPI specification.

### What are Camunda Connectors?

The [Camunda Integration Framework](https://docs.camunda.io/docs/next/components/integration-framework/introduction-to-connectors/) aims to provide connectivity with any arbitrary outside system. The Camunda HTTP/JSON API allows to connect and orchestrate any HTTP API with JSON payload, including REST APIs. 

### What is OpenAPI?

The OpenAPI standard defines a language-agnostic interface to REST APIs. Many APIs, including many popular ones, offer their users a OpenAPI specification of their API for convenience. The OpenAPI project comes with a [generator](https://github.com/OpenAPITools/openapi-generator), that allows to create server stubs and client libraries for a majority of languages. The project is open-source and allows for extensibility.

## Usage

Run it as follows:

```sh
sh ./generate.sh -i schema.yaml -o targetDir
```

where `schema.yaml` is your OpenAPI schema, and `targetDir` is your targeted output directory for the Connector templates. Both JSON and YAML schemas are supported.

When you receive the message `GENERATOR SUCCESS`, you can find your templates in the output dir.

> ℹ️ Before using this templates in production, it is strongly recommended to clean them up!

Run `sh ./generate.sh -h` for an overview of other arguments.

## Requirements

You need [Maven](https://maven.apache.org/) and [jq](https://stedolan.github.io/jq/) running on your machine.

## Modifying the Generator

To modify the generator, it is most of the times sufficient to change the mustache templates in `generators/connectors-codegen/src/main/resources/templates`. For more complex adjustments, you also need to update the generator Java classes in `generators/connectors-codegen/src/main/java/io/camunda/connectors/codegen/`. Also see the [OpenAPI generator reference docs](https://openapi-generator.tech/docs/templating), as well as [this overview of mustache variables](https://github.com/swagger-api/swagger-codegen/wiki/Mustache-Template-Variables), and the [example templates](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources) and [classes](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/java/org/openapitools/codegen/languages).
