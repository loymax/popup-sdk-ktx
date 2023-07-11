package loymax.popup.sdk.apis

import loymax.popup.sdk.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import loymax.popup.sdk.models.ConfirmRequest
import loymax.popup.sdk.models.PopupResponse
import loymax.popup.sdk.models.PopupRequest

import loymax.popup.sdk.models.*

interface PopupApi {
    /**
     * Confirm action popup
     * Method to confirm action and remove event to show popup
     * Responses:
     *  - 204: successful operation
     *
     * @param confirmRequest  (optional)
     * @return [Call]<[Unit]>
     */
    @POST("popup/confirm")
    fun popupConfirm(@Body confirmRequest: ConfirmRequest? = null): Call<Unit>

    /**
     * Find popup by client_id
     * Returns parameters for rendering popup
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid ID supplied
     *
     * @param popupRequest  (optional)
     * @return [Call]<List<[PopupResponse]>>
     */
    @POST("popup/")
    fun popup(@Body popupRequest: PopupRequest? = null): Call<List<PopupResponse>>

    /**
     * Post a web event
     * Method to post a web event data
     * Responses:
     *  - 200: Web event has been successfully put into queue
     *
     * @param eventRequest  (optional)
     * @return [Call]<[Unit]>
     */
    @POST("web_event/")
    fun event(@Body eventRequest: EventRequest? = null): Call<Unit>
}