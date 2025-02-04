# Loymax SDK - Kotlin Client Library for Smart Communication API & Popup SDK

## Overview

Loymax SDK объединяет API для работы с **Smart Communication** и **Popup SDK**.

- **Smart Communication API** предоставляет методы для работы с push-уведомлениями, статусами подписок клиентов и авторизацией.
- **Popup SDK** используется для работы с popup-уведомлениями и отправкой событий.

## Requires

- **Kotlin**: 1.8.10 (Popup SDK) / 1.7.21 (Smart Communication API)
- **Gradle**: 8.0+

---

## Installation

### 1. Добавьте в `settings.gradle`
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. Добавьте зависимости в `build.gradle`
```kotlin
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.moshi:moshi-adapters:1.13.0"
implementation "com.github.loymax:popup-sdk-ktx:1.0.9"
```

---

## Features / Implementation Notes

- Поддерживает JSON, FormData, File inputs.
- Поддержка коллекционных форматов параметров: csv, tsv, ssv, pipes.
- Оптимизирован для Android с минимальным количеством методов в `ApiClient`.

---

## API Documentation

### **Popup API**
| Class       | Method    | HTTP Request         | Description                            |
|------------|----------|----------------------|----------------------------------------|
| **PopupApi** | `confirm` | **POST** /popup/confirm | Подтверждение действия popup          |
| **PopupApi** | `popup`   | **POST** /popup/     | Найти popup по client_id              |
| **PopupApi** | `event`   | **POST** /web_event/ | Отправка события                      |

### **Smart Communication API**
| Class            | Method    | HTTP Request                          | Description                                      |
|-----------------|----------|--------------------------------------|--------------------------------------------------|
| **AuthorizationApi** | `getAuthToken` | **POST** /token                      | Авторизация в системе SMC                      |
| **CommunicationApi** | `logPushEvent` | **POST** /communication/{type}/events | Отправка события push                         |
| **CustomerApi**      | `addCustomerToken` | **POST** /customer/{id}/contact       | Отправка push-токена                          |
| **CustomerApi**      | `getCustomerNotificationStatus` | **GET** /customer/{id}/channel       | Получение статуса уведомлений                 |
| **CustomerApi**      | `getCustomerSubscriptionCategories` | **GET** /customer/{id}/subscribe     | Получение подписок клиента                    |
| **CustomerApi**      | `modifyCustomerSubscriptionCategories` | **PATCH** /customer/{id}/subscribe   | Изменение подписок клиента                    |
| **CustomerApi**      | `setCustomerNotificationStatus` | **POST** /customer/{id}/channel      | Изменение разрешений уведомлений              |

---

## Usage

### **Инициализация `ApiClient` и `PopUpService`**
```kotlin
val okHttpClient = OkHttpClient().newBuilder()
val apiClient = ApiClient(TokenManager.getBaseUrl(), okHttpClient)
apiClient.addAuthorization("Authorization", HttpBearerAuth("Bearer", TokenManager.getToken()))
apiClient.setBearerToken(TokenManager.getToken() ?: "")
val popUpService = PopUpService(apiClient)
```

### **Работа с Popup API**
```kotlin
val result = popUpService.popup(clientId, action, reference)
val result = popUpService.popupConfirm(ConfirmRequest)
val result = popUpService.event(EventRequest)
```

### **Работа с Smart Communication API**

#### **Авторизация**
```kotlin
val authApi = apiClient.createService(AuthorizationApi::class.java)
val authRequest = GetAuthTokenRequest(
    data = GetAuthTokenRequestData(
        attributes = GetAuthTokenRequestDataAttributes(username, password)
    )
)
val response = authApi.getAuthToken(authRequest).execute()
```

#### **Логирование push-события**
```kotlin
val communicationApi = apiClient.createService(CommunicationApi::class.java)
val logRequest = LogPushEventRequest(
    data = listOf(LogPushEventRequestDataInner(type, messageId, externalClientId))
)
val response = communicationApi.logPushEvent(type, logRequest).execute()
```

#### **Добавление push-токена клиента**
```kotlin
val customerApi = apiClient.createService(CustomerApi::class.java)
val tokenRequest = AddCustomerTokenRequest(
    data = AddCustomerTokenRequestData(
        attributes = AddCustomerTokenRequestDataAttributes(
            contacts = listOf(
                AddCustomerTokenRequestDataAttributesContactsInner(
                    type = "push",
                    subtype = "fcm",
                    value = TokenManager.getFirebaseToken(),
                    device = "android"
                )
            )
        )
    )
)
val response = customerApi.addCustomerToken(customerId, tokenRequest).execute()
```

---

## Documentation for Models

- `AddCustomerTokenRequest`
- `AddCustomerTokenRequestData`
- `LogPushEventRequest`
- `GetAuthTokenRequest`
- `CustomerNotificationStatus`
- `CustomerSubscriptionCategories`

---

## Authentication

### **bearerAuth (JWT)**

- Используется токен авторизации Bearer Token.
- Токен сохраняется в `TokenManager` и добавляется автоматически.

```kotlin
apiClient.setBearerToken(TokenManager.getToken() ?: "")
```

---

## Build & Testing

### **Сборка проекта**
```sh
gradle wrapper
./gradlew check assemble
```

### **Тестирование**
```sh
./gradlew test
```

---

## License

Loymax SDK is licensed under the MIT License.