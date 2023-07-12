package loymax.popup.sdk.services

import loymax.popup.sdk.apis.PopupApi
import loymax.popup.sdk.infrastructure.ApiClient
import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.EventRequest
import loymax.popup.sdk.models.PopupRequest
import loymax.popup.sdk.models.PopupResponse

open class PopUpService(private val loymaxClient: ApiClient): IPopUpService {
    private val popupApi: PopupApi = loymaxClient.createService(PopupApi::class.java)

    override fun popup(clientId: String?, action: String?, reference: String?): retrofit2.Response<List<PopupResponse>> {
        return popupApi.popup(PopupRequest(clientId,action,reference)).execute()
    }

    override fun popupConfirm(confirmRequest: ConfirmRequest): retrofit2.Response<Unit> {
        return popupApi.popupConfirm(confirmRequest).execute()
    }

    override fun event(eventRequest: EventRequest): retrofit2.Response<Unit> {
        return popupApi.event(eventRequest).execute()
    }
}