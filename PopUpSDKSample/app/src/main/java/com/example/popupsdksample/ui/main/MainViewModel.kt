package com.example.popupsdksample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loymax.popup.sdk.auth.HttpBearerAuth
import loymax.popup.sdk.infrastructure.ApiClient
import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.EventRequest
import loymax.popup.sdk.services.PopUpService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val _baseUrl = "https://api.loymaxsc.net/"

    private var _popUpService: PopUpService
    private var _loymaxClient: ApiClient

    init {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .also {
                it.addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }

        _loymaxClient = ApiClient(_baseUrl, okHttpClient)
        _loymaxClient.addAuthorization("Authorization", HttpBearerAuth("Bearer",""))
        _loymaxClient.setBearerToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkubG95bWF4c2MubmV0XC90b2tlbiIsImlhdCI6MTY4OTA4OTA1OSwiZXhwIjoxNjg5MDkyNjU5LCJuYmYiOjE2ODkwODkwNTksImp0aSI6ImlTbVFtenNvODJZb042MXAiLCJzdWIiOjcwOCwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.klvt81k61o32o-j6kg-Zgf3yqxBEK5c1zD6zEcrW-_0")
        _popUpService = PopUpService(_loymaxClient)
    }

    private var _popUpContextLive: MutableLiveData<String> = MutableLiveData()
    val popUpContextLive: LiveData<String> = _popUpContextLive

    fun getPopUp(clientId: String, action: String, reference: String) {
        _popUpContextLive.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.popup(clientId, action, reference)
                if (result.isSuccessful) {
                    var body = ""
                    result.body()?.forEach {
                        body += it.toString()
                    }
                    _popUpContextLive.postValue("body: $body")
                } else {
                    _popUpContextLive.postValue("Error ${result.errorBody() ?: "result is null"}")
                }
            } catch (ex: Exception) {
                _popUpContextLive.postValue("error: ${ex.message}")
            }
        }
    }

    private var _popUpConfirmLive: MutableLiveData<String> = MutableLiveData()
    var popUpConfirmLive: LiveData<String> = _popUpConfirmLive

    fun popUpConfirm() {
        _popUpConfirmLive.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.popupConfirm(getConfirmModel())
                if (result.isSuccessful) {
                    _popUpConfirmLive.postValue("Confirm: successful")
                } else {
                    _popUpConfirmLive.postValue("Error ${result.errorBody() ?: "result is null"}")
                }
            } catch (ex: Exception) {
                _popUpConfirmLive.postValue("${ex.message}")
            }
        }
    }

    private var _sendEventLive: MutableLiveData<String> = MutableLiveData()
    var sendEventLive: LiveData<String> = _sendEventLive

    fun sendEvent() {
        _sendEventLive.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.event(getEvent())
                if (result.isSuccessful) {
                    _sendEventLive.postValue("Confirm: successful")
                } else {
                    _sendEventLive.postValue("Error ${result.errorBody() ?: "result is null"}")
                }
            } catch (ex: Exception) {
                _sendEventLive.postValue("${ex.message}")
            }
        }
    }

    private fun getConfirmModel(): ConfirmRequest {
        return ConfirmRequest(123, "123", 123)
    }

    private fun getEvent(): EventRequest {
        return EventRequest("")
    }
}