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
 * @param errors Ошибки ограничения нагрузки на API.
 */



data class RateLimitErrorResponse (

    /* Ошибки ограничения нагрузки на API. */
    @Json(name = "errors")
    val errors: kotlin.collections.List<RateLimitErrorResponseErrorsInner>? = null

) : Serializable {


}

