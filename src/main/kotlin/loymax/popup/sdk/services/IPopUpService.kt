package loymax.popup.sdk.services

import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.PopupResponse

interface IPopUpService {
   fun initialization()
   fun popUp(clientId: String?, action: String?, reference: String?): retrofit2.Response<PopupResponse>
   fun viewPopUp(confirmRequest: ConfirmRequest): retrofit2.Response<Unit>
}