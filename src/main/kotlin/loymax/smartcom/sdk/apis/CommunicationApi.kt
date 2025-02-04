package loymax.smartcom.sdk.apis

import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.squareup.moshi.Json
import loymax.smartcom.sdk.models.LogPushEvent200Response
import loymax.smartcom.sdk.models.LogPushEventRequest

interface CommunicationApi {
    /**
     * Передать в Смарт факт показа (открытия) пуша
     * Передать факт показа (открытия) пуша
     * Responses:
     *  - 200: Успешный ответ
     *  - 400: Неверный тип коммуникации
     *
     * @param type Тип коммуникации (email/sms/push/bot)
     * @param logPushEventRequest  (optional)
     * @return [Call]<[LogPushEvent200Response]>
     */
    @POST("communication/{type}/events")
    fun logPushEvent(@Path("type") type: kotlin.String, @Body logPushEventRequest: LogPushEventRequest? = null): Call<LogPushEvent200Response>

}
