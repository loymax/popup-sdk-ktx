# PopupApi

All URIs are relative to *https://api.loymaxsc.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**confirm**](PopupApi.md#confirm) | **POST** popup/confirm | Confirm action popup
[**popupClientIdGet**](PopupApi.md#popupClientIdGet) | **GET** popup/{clientId} | Find popup by client_id



Confirm action popup

Method to confirm action and remove event to show popup

### Example
```kotlin
// Import classes:
//import loymax.popup.sdk.*
//import loymax.popup.sdk.infrastructure.*
//import loymax.popup.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PopupApi::class.java)
val confirmRequest : ConfirmRequest =  // ConfirmRequest | 

webService.confirm(confirmRequest)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **confirmRequest** | **ConfirmRequest**|  | [optional]

### Return type

null (empty response body)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


Find popup by client_id

Returns parameters for rendering popup

### Example
```kotlin
// Import classes:
//import loymax.popup.sdk.*
//import loymax.popup.sdk.infrastructure.*
//import loymax.popup.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PopupApi::class.java)
val clientId : kotlin.String = clientId_example // kotlin.String | Client ID in the Loyalty Program
val action : kotlin.Any = Object // kotlin.Any | Target action ID

val result : PopupClientIdGet200Response = webService.popupClientIdGet(clientId, action)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clientId** | **kotlin.String**| Client ID in the Loyalty Program |
 **action** | **kotlin.Any**| Target action ID |

### Return type

**PopupClientIdGet200Response**

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

