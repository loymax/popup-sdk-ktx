/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package loymax.smartcom.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


/**
 * Тело запроса должно содержать массив статусов, где type: delivered/open/click/spam/unsubscribe,               message_id - идентификатор сообщения, external_client_id - идентификатор клиента.
 *
 * @param `data` 
 */



data class LogPushEventRequest (

    @Json(name = "data")
    val `data`: kotlin.collections.List<LogPushEventRequestDataInner>? = null

) : Serializable {


}

