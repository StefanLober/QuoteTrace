// To parse the JSON, install Klaxon and do:
//
//   val yahooFinanceJson = YahooFinanceJson.fromJson(jsonString)

package de.stefanlober.stocktrace.quoteproviders.json

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class YahooFinanceJson (
    val quoteResponse: QuoteResponse
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<YahooFinanceJson>(json)
    }
}

data class QuoteResponse (
    val result: List<Result>,
    val error: Any? = null
)

data class Result (
    val currency: String,
    val longName: String,
    val regularMarketPrice: Double
)
