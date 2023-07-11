package loymax.popup.sdk.services

import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.EventRequest
import loymax.popup.sdk.models.PopupResponse

interface IPopUpService {
   fun initialization()
   fun popup(clientId: String?, action: String?, reference: String?): retrofit2.Response<List<PopupResponse>>
   fun popupConfirm(confirmRequest: ConfirmRequest): retrofit2.Response<Unit>
   fun event(eventRequest: EventRequest): retrofit2.Response<Unit>
}