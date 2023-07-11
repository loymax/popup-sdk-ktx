package com.example.popupsdksample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loymax.popup.sdk.auth.HttpBearerAuth
import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.PopupResponse
import loymax.popup.sdk.services.PopUpService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private var _popUpService: PopUpService = PopUpService("https://api.loymaxsc.net/",
        OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .also {
                it.addInterceptor (HttpBearerAuth("Bearer","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkubG95bWF4c2MubmV0XC90b2tlbiIsImlhdCI6MTY4NTQzNDkyMCwiZXhwIjoxNjg1NDM4NTIwLCJuYmYiOjE2ODU0MzQ5MjAsImp0aSI6Ims5aklQeE90cjJaNHptZjUiLCJzdWIiOjcwOCwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.jXWafOzx4fcTgb1-fP3Odmkjh3W3jziR8XrlqpvAW4M"))
                it.addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            })

    init {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .also {
                it.addInterceptor (HttpBearerAuth("Bearer","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkubG95bWF4c2MubmV0XC90b2tlbiIsImlhdCI6MTY4NTQzNDkyMCwiZXhwIjoxNjg1NDM4NTIwLCJuYmYiOjE2ODU0MzQ5MjAsImp0aSI6Ims5aklQeE90cjJaNHptZjUiLCJzdWIiOjcwOCwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.jXWafOzx4fcTgb1-fP3Odmkjh3W3jziR8XrlqpvAW4M"))
                it.addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
        _popUpService.
    }

    private var _popUp: List<PopupResponse>? = null

    var popUpContext =  MutableLiveData<String>()
        private set
    var popUpConfirm = MutableLiveData<String>()
        private set

    fun getPopUp(clientId:String, action:String, reference:String) {
        popUpContext.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.popup(clientId,action,reference)
                if (result.isSuccessful) {
                    var body:String = ""
                    result.body()?.forEach { body+=it.toString() }
                    popUpContext.postValue("body: $body")
                }
                else {
                    popUpContext.postValue("Error ${result?.errorBody() ?: "result is null"}")
                }
            }
            catch (ex: Exception) {
                popUpContext.postValue("error: ${ex.message}")
            }
        }
    }

    fun popUpConfirm() {
        popUpConfirm.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.popupConfirm(getConfirmModel())
                if (result.isSuccessful) {
                    popUpConfirm.postValue("Confirm: successful")
                }
                else {
                    popUpConfirm.postValue("Error ${result.errorBody() ?: "result is null"}")
                }
            }
            catch (ex: Exception) {
                popUpConfirm.postValue("${ex.message}")
            }
        }
    }

    private fun getConfirmModel(): ConfirmRequest {
        return ConfirmRequest(123, "123", 123)
    }
}