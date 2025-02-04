package com.example.popupsdksample.ui.smc

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import loymax.smartcom.sdk.apis.SMCClient
import loymax.smartcom.sdk.models.AuthRequest
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.popupsdksample.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import loymax.smartcom.sdk.apis.SMCClientFactory
import loymax.smartcom.sdk.models.AddContactsRequest
import loymax.smartcom.sdk.models.AddContactsResponse
import loymax.smartcom.sdk.models.CustomerChannelsResponse
import loymax.smartcom.sdk.models.CustomerSubscriptionsResponse
import loymax.smartcom.sdk.models.UpdateChannelRequest
import loymax.smartcom.sdk.models.UpdateChannelResponse
import loymax.smartcom.sdk.models.UpdateResponse
import loymax.smartcom.sdk.models.UpdateSubscriptionsRequest


class SmcViewModel(application: Application) : AndroidViewModel(application) {
    val _baseUrl = MutableStateFlow("https://smcmaster-api.smc.nsk-k8s.loymax.net/")
    val baseUrl: StateFlow<String> = _baseUrl
    private var apiClient: SMCClient = SMCClientFactory.create(_baseUrl.value)

    var _userName: String = ""
    var _password: String = ""
    var _userId: String = ""
    private val tokenManager = TokenManager
//    "username": "info@kuzprof.ru",
//    "password": "bookean"

    private val _customerChannels = MutableStateFlow<CustomerChannelsResponse?>(null)
    val customerChannels: StateFlow<CustomerChannelsResponse?> = _customerChannels

    private val _updateChannelsResponse = MutableStateFlow<UpdateChannelResponse?>(null)
    val updateChannelsResponse: StateFlow<UpdateChannelResponse?> = _updateChannelsResponse

    private val _customerSubscriptions = MutableStateFlow<CustomerSubscriptionsResponse?>(null)
    val customerSubscriptions: StateFlow<CustomerSubscriptionsResponse?> = _customerSubscriptions

    private val _updateSubscriptionsResponse = MutableStateFlow<UpdateResponse?>(null)
    val updateSubscriptionsResponse: StateFlow<UpdateResponse?> = _updateSubscriptionsResponse

    private val _addContactsResponse = MutableStateFlow<AddContactsResponse?>(null)
    val addContactsResponse: StateFlow<AddContactsResponse?> = _addContactsResponse

//    val apiClient = SMCClientFactory.get()
    fun changeBaseUrl(url: String) {
        _baseUrl.value = url
        apiClient = SMCClientFactory.create(url)
    }

    fun authenticateUser(
        userName: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiClient.service.authenticate(
                        AuthRequest(
                            AuthRequest.Data(
                                AuthRequest.Data.Attributes(
                                    userName, password
                                )
                            )
                        )
                    ).execute()
                }

                if (response.isSuccessful) {
                    val token = response.body()?.data?.attributes?.access_token
                    if (token != null) {
                        tokenManager.saveToken(token)
                        println("Token saved: $token")
                        println("Token get: ${getToken()}")
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Unknown error occurred: ${e.message}")
            }
        }
    }

    fun getCustomerChannels(customerId: String) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    apiClient.service.getCustomerChannels(customerId, token).execute()
                }

                if (response.isSuccessful) {
                    _customerChannels.value = response.body()
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Unknown error: ${e.message}")
            }
        }
    }

    fun updateCustomerChannels(
        customerId: String,
        updateChannelsData: MutableList<Pair<String, String>>
    ) {
        println("updateCustomerChannels: $updateChannelsData")
        val emailValue = updateChannelsData.find { it.first == "email" }?.second ?: "N"
        val smsValue = updateChannelsData.find { it.first == "sms" }?.second ?: "N"
        val pushValue = updateChannelsData.find { it.first == "push" }?.second ?: "N"

        val updateChannelRequest = UpdateChannelRequest(
            data = UpdateChannelRequest.Data(
                attributes = UpdateChannelRequest.Data.Attributes(
                    email = emailValue,
                    sms = smsValue,
                    push = pushValue
                )
            )
        )

        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    apiClient.service.updateCustomerChannels(customerId, token, updateChannelRequest).execute()
                }

                if (response.isSuccessful) {
                    _updateChannelsResponse.value = response.body()
                    println("Update successful: $response")
                } else {
                    println("Error updating channels: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Unknown error updating channels: ${e.message}")
            }
        }
    }

    fun getCustomerSubscriptions(customerId: String) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    apiClient.service.getCustomerSubscriptions(customerId, token).execute()
                }

                if (response.isSuccessful) {
                    _customerSubscriptions.value = response.body()
                    println("_customerSubscriptions: ${_customerSubscriptions.value}")
                } else {
                    println("Error fetching subscriptions: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Unknown error fetching subscriptions: ${e.message}")
            }
        }
    }

    fun updateCustomerSubscriptions(customerId: String, updateSubscriptionsRequest: UpdateSubscriptionsRequest) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    apiClient.service.updateCustomerSubscriptions(customerId, token, updateSubscriptionsRequest).execute()
                }

                if (response.isSuccessful) {
                    _updateSubscriptionsResponse.value = response.body()
                } else {
                    println("Error updating subscriptions: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Unknown error updating subscriptions: ${e.message}")
            }
        }
    }

    fun addCustomerContacts(customerId: String, addContactsRequest: AddContactsRequest) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    apiClient.service.addCustomerContacts(customerId, token, addContactsRequest).execute()
                }

                if (response.isSuccessful) {
                    _addContactsResponse.value = response.body()
                } else {
                    println("Error adding contacts: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Unknown error adding contacts: ${e.message}")
            }
        }
    }

    fun getToken(): String? {
        return tokenManager.getToken()
    }
}
