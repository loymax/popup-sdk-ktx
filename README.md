# loymax.popup.sdk 

## Requires

* Kotlin 1.6.10
* Gradle 7.5

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*PopupApi* | [**confirm**](docs/PopupApi.md#confirm) | **POST** popup/confirm | Confirm action popup
*PopupApi* | [**popup**](docs/PopupApi.md#popup) | **POST** popup/ | Find popup by client_id

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
  implementation 'com.github.loymax:popup-sdk-ktx:1.0.5'
 
* set base url
  ```kotlin
  private var _popUpService: PopUpService = PopUpService(_baseUrl, httpClient)
  
p.s
You can also add httpclient.builder to PopUpService with baseUrl

* getPopUp and viewPopUp
  ```kotlin
  val result = _popUpService.popUp(clientId, action, reference)
  val result = _popUpService.popupConfirm(ConfirmRequest())
  
For more information, you can view our project PopUpSDKSample
    
