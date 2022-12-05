package loymax.popup.sdk.services

import loymax.popup.sdk.apis.PopupApi
import loymax.popup.sdk.infrastructure.ApiClient
import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.Popup
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class PopUpService(_baseUrl: String): IPopUpService {
    private val baseUrl: String = _baseUrl
    private var httpClient: OkHttpClient.Builder? = null
    private lateinit var loymaxClient: ApiClient
    private lateinit var popupApi: PopupApi

    constructor(_baseUrl: String, _httpClient: OkHttpClient.Builder? ) : this(_baseUrl) {
        httpClient = _httpClient
    }
    override fun initialization() {
        if (httpClient == null) {
            httpClient = OkHttpClient()
                .newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
        }

        loymaxClient = ApiClient(baseUrl, httpClient)
        popupApi = loymaxClient.createService(PopupApi::class.java)
    }

    override fun getPopUp(clientId: String, action: String): retrofit2.Response<Popup> {
        if(!::loymaxClient.isInitialized) {
            initialization()
        }
        return popupApi.popupClientIdGet(clientId, action).execute()
    }

    override fun viewPopUp(confirmRequest: ConfirmRequest): retrofit2.Response<Unit> {
        if(!::loymaxClient.isInitialized) {
            initialization()
        }
        return popupApi.confirm(confirmRequest).execute()
    }
}