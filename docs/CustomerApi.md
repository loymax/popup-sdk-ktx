# CustomerApi

All URIs are relative to *https://smcmaster-api.smc.nsk-k8s.loymax.net/api*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**addCustomerToken**](CustomerApi.md#addCustomerToken) | **POST** customer/{id}/contact | Передать в Смарт полученный от APNS/FCM/rustore/HuaweiPushToolkit токен |
| [**getCustomerNotificationStatus**](CustomerApi.md#getCustomerNotificationStatus) | **GET** customer/{id}/channel | Получить из Смарта статус разрешения на уведомления клиенту |
| [**getCustomerSubscriptionCategories**](CustomerApi.md#getCustomerSubscriptionCategories) | **GET** customer/{id}/subscribe | Получить из Смарта статус подписки клиента на категории рассылок |
| [**modifyCustomerSubscriptionCategories**](CustomerApi.md#modifyCustomerSubscriptionCategories) | **PATCH** customer/{id}/subscribe | Передать в Смарт статус подписки на категории |
| [**setCustomerNotificationStatus**](CustomerApi.md#setCustomerNotificationStatus) | **POST** customer/{id}/channel | Передать в Смарт статус разрешения на уведомления клиенту |



Передать в Смарт полученный от APNS/FCM/rustore/HuaweiPushToolkit токен

Вставить контактные данные клиента

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CustomerApi::class.java)
val id : kotlin.String = id_example // kotlin.String | Внешний идентификатор клиента (client_external_id)
val addCustomerTokenRequest : AddCustomerTokenRequest =  // AddCustomerTokenRequest | Тело запроса должно содержать массив контактных данных, где `type` и `value` обязательные.

webService.addCustomerToken(id, addCustomerTokenRequest)
```

### Parameters
| **id** | **kotlin.String**| Внешний идентификатор клиента (client_external_id) | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **addCustomerTokenRequest** | [**AddCustomerTokenRequest**](AddCustomerTokenRequest.md)| Тело запроса должно содержать массив контактных данных, где &#x60;type&#x60; и &#x60;value&#x60; обязательные. | [optional] |

### Return type

null (empty response body)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


Получить из Смарта статус разрешения на уведомления клиенту

Возвращает разрешения на уведомления клиенту. При запросе коллекции сущностей в /GET параметрах можно передать: &#x60;?sort&#x3D;[{\&quot;attribute\&quot;: \&quot;name\&quot;, \&quot;direction\&quot;: \&quot;asc\&quot;},...]&#x60; Это JSON-массив объектов с условиями сортировки. - **attribute**: Имя атрибута модели. - **direction**: Направление сортировки. Возможные значения: &#x60;asc&#x60;, &#x60;desc&#x60;. 

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CustomerApi::class.java)
val id : kotlin.String = id_example // kotlin.String | Внешний идентификатор клиента (client_external_id)

val result : GetCustomerNotificationStatus200Response = webService.getCustomerNotificationStatus(id)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Внешний идентификатор клиента (client_external_id) | |

### Return type

[**GetCustomerNotificationStatus200Response**](GetCustomerNotificationStatus200Response.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Получить из Смарта статус подписки клиента на категории рассылок

Внимание! Если клиент не подписан на какую-либо из категорий - она не будет отображаться (&#x60;data.attributes&#x60; динамическое). В случае полного отсутствия подписок у клиента - вернется пустой массив &#x60;attributes&#x60;. 

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CustomerApi::class.java)
val id : kotlin.String = id_example // kotlin.String | Внешний идентификатор клиента (client_external_id)

val result : GetCustomerSubscriptionCategories200Response = webService.getCustomerSubscriptionCategories(id)
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **id** | **kotlin.String**| Внешний идентификатор клиента (client_external_id) | |

### Return type

[**GetCustomerSubscriptionCategories200Response**](GetCustomerSubscriptionCategories200Response.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Передать в Смарт статус подписки на категории

Метод для управления подпиской клиента по его идентификатору в мастер-системе. Подписка на категории рассылок, по которым код не передан не обновляется.

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CustomerApi::class.java)
val id : kotlin.String = id_example // kotlin.String | Внешний идентификатор клиента (client_external_id)
val modifySubscriptionRequest : ModifySubscriptionRequest =  // ModifySubscriptionRequest | В `categories` передаются одна или более категорий рассылок, содержащей пары ключ-значение, где ключ - это код категории рассылки (`mailingCode`), а значение - это объект, содержащий пары ключ-значение, где ключ - это тип массовых рассылок, а значение - статус подписки (`Y` - подписан, `N` - отписан, `ND` - удалить из категории). 

val result : SMCSuccessResponse = webService.modifyCustomerSubscriptionCategories(id, modifySubscriptionRequest)
```

### Parameters
| **id** | **kotlin.String**| Внешний идентификатор клиента (client_external_id) | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **modifySubscriptionRequest** | [**ModifySubscriptionRequest**](ModifySubscriptionRequest.md)| В &#x60;categories&#x60; передаются одна или более категорий рассылок, содержащей пары ключ-значение, где ключ - это код категории рассылки (&#x60;mailingCode&#x60;), а значение - это объект, содержащий пары ключ-значение, где ключ - это тип массовых рассылок, а значение - статус подписки (&#x60;Y&#x60; - подписан, &#x60;N&#x60; - отписан, &#x60;ND&#x60; - удалить из категории).  | [optional] |

### Return type

[**SMCSuccessResponse**](SMCSuccessResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


Передать в Смарт статус разрешения на уведомления клиенту

Изменить/добавить подписки клиента на каналы коммуникаций

### Example
```kotlin
// Import classes:
//import com.smartcommunications.sdk.*
//import com.smartcommunications.sdk.infrastructure.*
//import com.smartcommunications.sdk.models.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(CustomerApi::class.java)
val id : kotlin.String = id_example // kotlin.String | Внешний идентификатор клиента (client_external_id)
val setCustomerNotificationStatusRequest : SetCustomerNotificationStatusRequest =  // SetCustomerNotificationStatusRequest | Атрибут должен содрежать пары ключ-значение, где ключ - это тип канала коммуникации,                    а значение - это статус разрешения на уведомления клиенту

val result : SMCSuccessResponse = webService.setCustomerNotificationStatus(id, setCustomerNotificationStatusRequest)
```

### Parameters
| **id** | **kotlin.String**| Внешний идентификатор клиента (client_external_id) | |
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **setCustomerNotificationStatusRequest** | [**SetCustomerNotificationStatusRequest**](SetCustomerNotificationStatusRequest.md)| Атрибут должен содрежать пары ключ-значение, где ключ - это тип канала коммуникации,                    а значение - это статус разрешения на уведомления клиенту | [optional] |

### Return type

[**SMCSuccessResponse**](SMCSuccessResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

