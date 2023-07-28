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
 * @param clientId 
 * @param sourceId 
 * @param sessionId 
 * @param date 
 * @param name 
 * @param pageName 
 * @param categoryId 
 * @param productId 
 * @param quantity 
 * @param price 
 * @param container 
 * @param eventName 
 */

data class EventResponse (

    @Json(name = "client_id")
    val clientId: kotlin.String? = null,

    @Json(name = "source_id")
    val sourceId: kotlin.String? = null,

    @Json(name = "session_id")
    val sessionId: kotlin.String? = null,

    @Json(name = "date")
    val date: kotlin.String? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "page_name")
    val pageName: kotlin.String? = null,

    @Json(name = "category_id")
    val categoryId: kotlin.String? = null,

    @Json(name = "product_id")
    val productId: kotlin.String? = null,

    @Json(name = "quantity")
    val quantity: java.math.BigDecimal? = null,

    @Json(name = "price")
    val price: java.math.BigDecimal? = null,

    @Json(name = "container")
    val container: kotlin.String? = null,

    @Json(name = "event_name")
    val eventName: kotlin.String? = null
)