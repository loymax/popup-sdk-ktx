# AuthorizationApi

All URIs are relative to *https://smcmaster-api.smc.nsk-k8s.loymax.net/api*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getAuthToken**](AuthorizationApi.md#getAuthToken) | **POST** token | Авторизация в системе SMC |



Авторизация в системе SMC

Получение токена для авторизации в системе SMC.

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(AuthorizationApi::class.java)
val getAuthTokenRequest : GetAuthTokenRequest =  // GetAuthTokenRequest | Учетные данные для получения токена.

val result : GetAuthToken200Response = webService.getAuthToken(getAuthTokenRequest)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **getAuthTokenRequest** | [**GetAuthTokenRequest**](GetAuthTokenRequest.md)| Учетные данные для получения токена. | |

### Return type

[**GetAuthToken200Response**](GetAuthToken200Response.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

