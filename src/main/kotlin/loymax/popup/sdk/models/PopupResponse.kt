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

package loymax.popup.sdk.models

import com.squareup.moshi.Json

/**
 * 
 *
 * @param popupId 
 * @param navlink 
 * @param reference 
 * @param content 
 * @param style 
 */

data class PopupResponse (

    @Json(name = "popup_id")
    val popupId: kotlin.Long,

    @Json(name = "navlink")
    val navlink: kotlin.String,

    @Json(name = "reference")
    val reference: kotlin.String? = null,

    @Json(name = "content")
    val content: PopupResponseContent? = null,

    @Json(name = "style")
    val style: PopupResponseStyle? = null

)

