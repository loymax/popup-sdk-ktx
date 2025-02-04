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
 * @param `data` 
 * @param errors Список ошибок.
 * @param links 
 */



data class GetAuthToken200Response (

    @Json(name = "data")
    val `data`: AuthTokenResponseData? = null,

    /* Список ошибок. */
    @Json(name = "errors")
    val errors: kotlin.collections.List<SMCErrorDetails>? = null,

    @Json(name = "links")
    val links: SMCLinks? = null

) : Serializable {


}

