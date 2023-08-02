# loymax.popup.sdk 

## Requires

* Kotlin 1.8.10
* Gradle 8

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*PopupApi* | [**confirm**](docs/PopupApi.md#confirm) | **POST** popup/confirm | Confirm action popup
*PopupApi* | [**popup**](docs/PopupApi.md#popup) | **POST** popup/ | Find popup by client_id
*PopupApi* | [**event**](docs/PopupApi.md#event) | **POST** web_event/ | send event

## How to use
* add settings.gradle
  ```kotlin
  dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
      repositories {
          google()
          mavenCentral()
          maven { url 'https://jitpack.io' }
      }
  }
* add libs to build.gradle
  ```kotlin
  implementation "com.squareup.retrofit2:retrofit:2.9.0"
  implementation "com.github.loymax:popup-sdk-ktx:1.0.9"
  implementation "com.squareup.moshi:moshi-adapters:1.13.0"
 
* initialize client and service 
  ```kotlin
  val okHttpClient = OkHttpClient().newBuilder()
  _loymaxClient = ApiClient(_baseUrl, okHttpClient)
  _loymaxClient.addAuthorization("Authorization", HttpBearerAuth("Bearer", "token"))
  _loymaxClient.setBearerToken("updatedToken")
  _popUpService = PopUpService(_loymaxClient)
  
p.s
You can also add httpclient.builder to PopUpService with baseUrl

* popup, popupConfirm and event
  ```kotlin
  val result = _popUpService.popup(clientId, action, reference)
  val result = _popUpService.popupConfirm(ConfirmRequest)
  val result = _popUpService.event(EventRequest)
  
For more information, you can view our project PopUpSDKSample
    
