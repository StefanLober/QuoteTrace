package de.stefanlober.quotetrace.quoteproviders

import com.beust.klaxon.Klaxon
import de.stefanlober.quotetrace.data.StockQuote
import de.stefanlober.quotetrace.quoteproviders.json.YahooFinanceJson
import java.io.InputStream
import java.math.BigDecimal
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection


class YahooFinanceQuoteProvider @Inject constructor() : IQuoteProvider {
    private val baseUrl = "https://query1.finance.yahoo.com/v7/finance/quote?symbols="
    private val userAgent = "Mozilla/5.0 (Linux; Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Mobile Safari/537.36"

    override fun getStockQuote(symbol: String): StockQuote {
        val endpoint = URL(baseUrl + URLEncoder.encode(symbol, "utf-8"))
        val connection: HttpsURLConnection = endpoint.openConnection() as HttpsURLConnection
        connection.setRequestProperty("User-Agent", userAgent);

        if (connection.responseCode == 200) {
            val inputStream: InputStream = connection.inputStream
            val yahooFinanceJson= Klaxon().parse<YahooFinanceJson>(inputStream)

            if (yahooFinanceJson != null) {
                val result = yahooFinanceJson.quoteResponse.result[0]
                val name = result.longName
                val amount = BigDecimal(result.regularMarketPrice)
                val currency = result.currency

                return StockQuote(name, amount, currency, true)
            }

            throw Exception("Error: Empty quote result")

        } else
            throw Exception("Error: YahooFinanceQuoteProvider response code " + connection.responseCode)
    }
}