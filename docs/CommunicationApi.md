# CommunicationApi

All URIs are relative to *https://smcmaster-api.smc.nsk-k8s.loymax.net/api*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**logPushEvent**](CommunicationApi.md#logPushEvent) | **POST** communication/{type}/events | Передать в Смарт факт показа (открытия) пуша |



Передать в Смарт факт показа (открытия) пуша

Передать факт показа (открытия) пуша

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CommunicationApi::class.java)
val type : kotlin.String = type_example // kotlin.String | Тип коммуникации (email/sms/push/bot)
val logPushEventRequest : LogPushEventRequest =  // LogPushEventRequest | 

val result : LogPushEvent200Response = webService.logPushEvent(type, logPushEventRequest)
```

### Parameters
| **type** | **kotlin.String**| Тип коммуникации (email/sms/push/bot) | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **logPushEventRequest** | [**LogPushEventRequest**](LogPushEventRequest.md)|  | [optional] |

### Return type

[**LogPushEvent200Response**](LogPushEvent200Response.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

