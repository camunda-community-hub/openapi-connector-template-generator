# ConnectorTemplatesExampleApis.TestApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**testOperation**](TestApi.md#testOperation) | **GET** /test | Test test



## testOperation

> InlineResponse200 testOperation(opts)

Test test

Just a test 

### Example

```javascript
import ConnectorTemplatesExampleApis from 'connector_templates_example_apis';

let apiInstance = new ConnectorTemplatesExampleApis.TestApi();
let opts = {
  'testQueryParam': "testQueryParam_example" // String | Test query param
};
apiInstance.testOperation(opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **testQueryParam** | **String**| Test query param | [optional] 

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

