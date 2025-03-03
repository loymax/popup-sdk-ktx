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
 * 
 *
 * @param status Код состояния HTTP.
 * @param title Сообщение об ошибке.
 * @param validation Дополнительная информация об ошибках валидации. Возможные ключи зависят от полей `requestBody` соответствующего эндпоинта. 
 */



data class SMCErrorDetails (

    /* Код состояния HTTP. */
    @Json(name = "status")
    val status: kotlin.Int? = null,

    /* Сообщение об ошибке. */
    @Json(name = "title")
    val title: kotlin.String? = null,

    /* Дополнительная информация об ошибках валидации. Возможные ключи зависят от полей `requestBody` соответствующего эндпоинта.  */
    @Json(name = "validation")
    val validation: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>? = null

) : Serializable {


}

