package com.example.popupsdksample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.Popup
import loymax.popup.sdk.services.PopUpService

class MainViewModel : ViewModel() {
    private var _popUpService: PopUpService = PopUpService("http://192.168.12.31:3000/")

    private var _popUp: Popup? = null

    var popUpContext =  MutableLiveData<String>()
        private set
    var popUpConfirm = MutableLiveData<String>()
        private set

    fun getPopUp(clientId:String, action:String) {
        popUpContext.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.getPopUp(clientId,action)
                if (result != null && result.isSuccessful) {
                    _popUp = result.body()
                    popUpContext.postValue("body: ${result.body().toString()}")
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

    fun viewPopUp() {
        popUpConfirm.value = "loading..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = _popUpService.viewPopUp(getConfirmModel())
                if (result != null && result.isSuccessful) {
                    popUpConfirm.postValue("Confirm: successful")
                }
                else {
                    popUpConfirm.postValue("Error ${result?.errorBody() ?: "result is null"}")
                }
            }
            catch (ex: Exception) {
                popUpConfirm.postValue("${ex.message}")
            }
        }
    }

    fun getConfirmModel() : ConfirmRequest {
        val confirmRequest = ConfirmRequest(123,"123",123)
        return confirmRequest
    }
}