package loymax.popup.sdk.services

import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.Popup
import retrofit2.http.Query

interface IPopUpService {
   fun initialization()
   fun getPopUp(clientId: String, action: String): retrofit2.Response<Popup>
   fun viewPopUp(confirmRequest: ConfirmRequest): retrofit2.Response<Unit>
}