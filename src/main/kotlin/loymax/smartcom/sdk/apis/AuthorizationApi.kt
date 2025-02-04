package loymax.smartcom.sdk.apis

import retrofit2.http.*
import retrofit2.Call
import loymax.smartcom.sdk.models.GetAuthToken200Response
import loymax.smartcom.sdk.models.GetAuthTokenRequest

interface AuthorizationApi {
    /**
     * Авторизация в системе SMC
     * Получение токена для авторизации в системе SMC.
     * Responses:
     *  - 200: Успешное получение токена.
     *  - 400: Ошибка в запросе.
     *  - 401: Клиент не найден.
     *  - 403: Доступ запрещен.
     *  - 404: Неверный формат данных.
     *  - 422: Ошибка валидации данных.
     *  - 429: Превышение лимита запросов.
     *  - 500: Внутренняя ошибка сервера.
     *
     * @param getAuthTokenRequest Учетные данные для получения токена.
     * @return [Call]<[GetAuthToken200Response]>
     */
    @POST("token")
    fun getAuthToken(@Body getAuthTokenRequest: GetAuthTokenRequest): Call<GetAuthToken200Response>

}
