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
 * @param type 
 * @param id 
 * @param attributes 
 */



data class CustomerNotificationStatusDataInner (

    @Json(name = "type")
    val type: kotlin.String? = null,

    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "attributes")
    val attributes: CustomerNotificationStatusDataInnerAttributes? = null

) : Serializable {


}

