package com.example.popupsdksample.ui.smc

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.popupsdksample.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import loymax.smartcom.sdk.apis.AuthorizationApi
import loymax.smartcom.sdk.apis.CommunicationApi
import loymax.smartcom.sdk.apis.CustomerApi
import loymax.smartcom.sdk.infrastructure.ApiClient
import loymax.smartcom.sdk.models.AddCustomerTokenRequest
import loymax.smartcom.sdk.models.GetAuthTokenRequest
import loymax.smartcom.sdk.models.GetAuthTokenRequestData
import loymax.smartcom.sdk.models.GetAuthTokenRequestDataAttributes
import loymax.smartcom.sdk.models.GetCustomerNotificationStatus200Response
import loymax.smartcom.sdk.models.GetCustomerSubscriptionCategories200Response
import loymax.smartcom.sdk.models.ModifySubscriptionRequest
import loymax.smartcom.sdk.models.ModifySubscriptionRequestData
import loymax.smartcom.sdk.models.ModifySubscriptionRequestDataAttributes
import loymax.smartcom.sdk.models.ModifySubscriptionRequestDataAttributesCategories
import loymax.smartcom.sdk.models.ModifySubscriptionRequestDataAttributesCategoriesMailingCode
import loymax.smartcom.sdk.models.SMCSuccessResponse
import loymax.smartcom.sdk.models.SetCustomerNotificationStatusRequest
import loymax.smartcom.sdk.models.SetCustomerNotificationStatusRequestData
import loymax.smartcom.sdk.models.SetCustomerNotificationStatusRequestDataAttributes


class SmcViewModel(application: Application) : AndroidViewModel(application) {
    //    "username": "info@kuzprof.ru",
    //    "password": "bookean"
    private val tokenManager = TokenManager

    val _baseUrl = MutableStateFlow(TokenManager.getBaseUrl())
    val baseUrl: StateFlow<String> = _baseUrl

    var _userName: String = ""
    var _password: String = ""
    var _userId: String = ""

    private var apiClient: ApiClient = createApiClient()

    private fun createApiClient(): ApiClient {
        val token = tokenManager.getToken() ?: ""
        return ApiClient(
            baseUrl = TokenManager.getBaseUrl(),
            bearerToken = token
        )
    }
    fun changeBaseUrl(url: String) {
        if (_baseUrl.value != url) {
            _baseUrl.value = url
            TokenManager.setBaseUrl(url)
            apiClient = createApiClient()
            println("🌍 Base URL changed to: $url")
        }
    }

    fun authenticateUser(userName: String, password: String) {
        viewModelScope.launch {
            try {
                val token = withContext(Dispatchers.IO) {
                    val authApi = apiClient.createService(AuthorizationApi::class.java)

                    val requestBody = GetAuthTokenRequest(
                        data = GetAuthTokenRequestData(
                            attributes = GetAuthTokenRequestDataAttributes(
                                username = userName,
                                password = password
                            )
                        )
                    )

                    val response = authApi.getAuthToken(requestBody).execute()

                    if (response.isSuccessful) {
                        response.body()?.data?.attributes?.accessToken
                    } else {
                        println("❌ Authentication failed: ${response.errorBody()?.string()}")
                        null
                    }
                }

                if (!token.isNullOrEmpty()) {
                    tokenManager.saveToken(token)
                    apiClient.setBearerToken(token) // Устанавливаем токен в ApiClient
                    println("✅ Token saved: $token")
                    println("🔑 Retrieved token: ${getToken()}")
                } else {
                    println("❌ Failed to retrieve token")
                }
            } catch (e: Exception) {
                println("❌ Unknown error occurred: ${e.message}")
            }
        }
    }

    private val _customerNotificationStatus = MutableStateFlow<GetCustomerNotificationStatus200Response?>(null)
    val customerNotificationStatus: StateFlow<GetCustomerNotificationStatus200Response?> = _customerNotificationStatus

    fun getCustomerNotificationStatus(customerId: String) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    val apiService = apiClient.createService(CustomerApi::class.java)
                    apiService.getCustomerNotificationStatus(customerId).execute()
                }

                if (response.isSuccessful) {
                    _customerNotificationStatus.value = response.body()
                    println("✅ Notification status received: ${_customerNotificationStatus.value}")
                } else {
                    println("❌ Error fetching notification status: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("❌ Unknown error fetching notification status: ${e.message}")
            }
        }
    }

    private val _customerNotificationStatusResponse = MutableStateFlow<SMCSuccessResponse?>(null)
    val customerNotificationStatusResponse: StateFlow<SMCSuccessResponse?> = _customerNotificationStatusResponse

    fun setCustomerNotificationStatus(
        customerId: String,
        notificationStatusData: MutableList<Pair<String, String>>
    ) {
        println("🔄 Updating customer notification status: $notificationStatusData")

        val emailValue = notificationStatusData.find { it.first == "email" }?.second ?: "N"
        val smsValue = notificationStatusData.find { it.first == "sms" }?.second ?: "N"
        val pushValue = notificationStatusData.find { it.first == "push" }?.second ?: "N"

        val requestBody = SetCustomerNotificationStatusRequest(
            data = SetCustomerNotificationStatusRequestData(
                attributes = SetCustomerNotificationStatusRequestDataAttributes(
                    email = emailValue,
                    push = pushValue,
                    sms = smsValue
                )
            )
        )

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val apiService = apiClient.createService(CustomerApi::class.java)
                    apiService.setCustomerNotificationStatus(customerId, requestBody).execute()
                }

                if (response.isSuccessful) {
                    _customerNotificationStatusResponse.value = response.body()
                    println("✅ Notification status updated: ${_customerNotificationStatusResponse.value}")
                } else {
                    println("❌ Error updating notification status: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("❌ Unknown error updating notification status: ${e.message}")
            }
        }
    }

    private val _customerSubscriptionCategories = MutableStateFlow<GetCustomerSubscriptionCategories200Response?>(null)
    val customerSubscriptionCategories: StateFlow<GetCustomerSubscriptionCategories200Response?> = _customerSubscriptionCategories

    fun getCustomerSubscriptionCategories(customerId: String) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    val apiService = apiClient.createService(CustomerApi::class.java)
                    apiService.getCustomerSubscriptionCategories(customerId).execute()
                }

                if (response.isSuccessful) {
                    _customerSubscriptionCategories.value = response.body()
                    println("✅ Subscription categories received: ${_customerSubscriptionCategories.value}")
                } else {
                    println("❌ Error fetching subscription categories: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("❌ Unknown error fetching subscription categories: ${e.message}")
            }
        }
    }

    private val _modifySubscriptionResponse = MutableStateFlow<SMCSuccessResponse?>(null)
    val modifySubscriptionResponse: StateFlow<SMCSuccessResponse?> = _modifySubscriptionResponse

    fun modifyCustomerSubscriptionCategories(customerId: String, modifySubscriptionRequest: ModifySubscriptionRequest) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    val apiService = apiClient.createService(CustomerApi::class.java)
                    apiService.modifyCustomerSubscriptionCategories(customerId, modifySubscriptionRequest).execute()
                }

                if (response.isSuccessful) {
                    _modifySubscriptionResponse.value = response.body()
                    println("✅ Subscription categories updated: ${_modifySubscriptionResponse.value}")
                } else {
                    println("❌ Error updating subscription categories: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("❌ Unknown error updating subscription categories: ${e.message}")
            }
        }
    }

    fun addCustomerToken(customerId: String, addCustomerTokenRequest: AddCustomerTokenRequest) {
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: ""
                val response = withContext(Dispatchers.IO) {
                    val apiService = apiClient.createService(CustomerApi::class.java)
                    apiService.addCustomerToken(customerId, addCustomerTokenRequest).execute()
                }

                if (response.isSuccessful) {
                    println("✅ Customer token successfully added!")
                } else {
                    println("❌ Error adding customer token: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("❌ Unknown error adding customer token: ${e.message}")
            }
        }
    }

    fun getToken(): String? {
        return tokenManager.getToken()
    }
}
