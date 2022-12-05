package loymax.popup.sdk.apis

import loymax.popup.sdk.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json

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
    fun confirm(@Body confirmRequest: ConfirmRequest? = null): Call<Unit>

    /**
     * Find popup by client_id
     * Returns parameters for rendering popup
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid ID supplied
     *
     * @param clientId Client ID in the Loyalty Program
     * @param action Target action ID
     * @return [Call]<[Popup]>
     */
    @GET("popup/{clientId}")
    fun popupClientIdGet(@Path("clientId") clientId: kotlin.String, @Query("action") action: kotlin.Any): Call<Popup>

}
