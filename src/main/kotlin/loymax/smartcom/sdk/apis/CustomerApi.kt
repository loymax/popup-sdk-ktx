package loymax.smartcom.sdk.apis

import loymax.smartcom.sdk.models.AddCustomerTokenRequest
import loymax.smartcom.sdk.models.GetCustomerNotificationStatus200Response
import loymax.smartcom.sdk.models.GetCustomerSubscriptionCategories200Response
import loymax.smartcom.sdk.models.ModifySubscriptionRequest
import loymax.smartcom.sdk.models.SMCSuccessResponse
import loymax.smartcom.sdk.models.SetCustomerNotificationStatusRequest
import retrofit2.Call
import retrofit2.http.*

interface CustomerApi {
    /**
     * Передать в Смарт полученный от APNS/FCM/rustore/HuaweiPushToolkit токен
     * Вставить контактные данные клиента
     * Responses:
     *  - 204: Успешный ответ без содержимого
     *
     * @param id Внешний идентификатор клиента (client_external_id)
     * @param addCustomerTokenRequest Тело запроса должно содержать массив контактных данных, где &#x60;type&#x60; и &#x60;value&#x60; обязательные. (optional)
     * @return [Call]<[Unit]>
     */
    @POST("customer/{id}/contact")
    fun addCustomerToken(@Path("id") id: kotlin.String, @Body addCustomerTokenRequest: AddCustomerTokenRequest? = null): Call<Unit>

    /**
     * Получить из Смарта статус разрешения на уведомления клиенту
     * Возвращает разрешения на уведомления клиенту. При запросе коллекции сущностей в /GET параметрах можно передать: &#x60;?sort&#x3D;[{\&quot;attribute\&quot;: \&quot;name\&quot;, \&quot;direction\&quot;: \&quot;asc\&quot;},...]&#x60; Это JSON-массив объектов с условиями сортировки. - **attribute**: Имя атрибута модели. - **direction**: Направление сортировки. Возможные значения: &#x60;asc&#x60;, &#x60;desc&#x60;. 
     * Responses:
     *  - 200: Основная часть ответа будет содержать массив `data`, который в свою очередь будет состоять                     из объектов, где в `attributes` будет содержаться информация о типе (`type`), подтипе (`subtype`) уведомлений,                     а также статус активности (`value`)
     *  - 404: Клиент не найден
     *
     * @param id Внешний идентификатор клиента (client_external_id)
     * @return [Call]<[GetCustomerNotificationStatus200Response]>
     */
    @GET("customer/{id}/channel")
    fun getCustomerNotificationStatus(@Path("id") id: kotlin.String): Call<GetCustomerNotificationStatus200Response>

    /**
     * Получить из Смарта статус подписки клиента на категории рассылок
     * Внимание! Если клиент не подписан на какую-либо из категорий - она не будет отображаться (&#x60;data.attributes&#x60; динамическое). В случае полного отсутствия подписок у клиента - вернется пустой массив &#x60;attributes&#x60;. 
     * Responses:
     *  - 200: Успешный ответ с данными
     *  - 404: Клиент не найден
     *
     * @param id Внешний идентификатор клиента (client_external_id)
     * @return [Call]<[GetCustomerSubscriptionCategories200Response]>
     */
    @GET("customer/{id}/subscribe")
    fun getCustomerSubscriptionCategories(@Path("id") id: kotlin.String): Call<GetCustomerSubscriptionCategories200Response>

    /**
     * Передать в Смарт статус подписки на категории
     * Метод для управления подпиской клиента по его идентификатору в мастер-системе. Подписка на категории рассылок, по которым код не передан не обновляется.
     * Responses:
     *  - 200: Успешный ответ
     *  - 404: Клиент не найден
     *
     * @param id Внешний идентификатор клиента (client_external_id)
     * @param modifySubscriptionRequest В &#x60;categories&#x60; передаются одна или более категорий рассылок, содержащей пары ключ-значение, где ключ - это код категории рассылки (&#x60;mailingCode&#x60;), а значение - это объект, содержащий пары ключ-значение, где ключ - это тип массовых рассылок, а значение - статус подписки (&#x60;Y&#x60; - подписан, &#x60;N&#x60; - отписан, &#x60;ND&#x60; - удалить из категории).  (optional)
     * @return [Call]<[SMCSuccessResponse]>
     */
    @PATCH("customer/{id}/subscribe")
    fun modifyCustomerSubscriptionCategories(@Path("id") id: kotlin.String, @Body modifySubscriptionRequest: ModifySubscriptionRequest? = null): Call<SMCSuccessResponse>

    /**
     * Передать в Смарт статус разрешения на уведомления клиенту
     * Изменить/добавить подписки клиента на каналы коммуникаций
     * Responses:
     *  - 200: Успешный ответ
     *  - 404: Клиент не найден
     *
     * @param id Внешний идентификатор клиента (client_external_id)
     * @param setCustomerNotificationStatusRequest Атрибут должен содрежать пары ключ-значение, где ключ - это тип канала коммуникации,                    а значение - это статус разрешения на уведомления клиенту (optional)
     * @return [Call]<[SMCSuccessResponse]>
     */
    @POST("customer/{id}/channel")
    fun setCustomerNotificationStatus(@Path("id") id: kotlin.String, @Body setCustomerNotificationStatusRequest: SetCustomerNotificationStatusRequest? = null): Call<SMCSuccessResponse>

}
