package de.stefanlober.quotetrace.quoteproviders

import de.stefanlober.quotetrace.data.StockQuote
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.math.BigDecimal
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection


class YahooFinanceQuoteProvider @Inject constructor() : IQuoteProvider {
    private val baseUrl = "https://query1.finance.yahoo.com/v6/finance/quote?symbols="
    private val userAgent = "Mozilla/5.0 (Linux; Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Mobile Safari/537.36"

    override fun getStockQuote(symbol: String): StockQuote {
        val endpoint = URL(baseUrl + URLEncoder.encode(symbol, "utf-8"))
        val connection: HttpsURLConnection = endpoint.openConnection() as HttpsURLConnection
        connection.setRequestProperty("User-Agent", userAgent);

        if (connection.responseCode == 200) {
            connection.inputStream.use { inputStream ->
                val jsonString = inputStreamToString(inputStream)
                val rootObject = JSONObject(jsonString)
                val quoteResponseObject = rootObject.getJSONObject("quoteResponse")
                val resultObject = quoteResponseObject.getJSONArray("result").getJSONObject(0)

                return StockQuote(resultObject.getString("longName"), BigDecimal(resultObject.getDouble("regularMarketPrice")), resultObject.getString("currency"), true)
            }

        } else
            throw Exception("Error: YahooFinanceQuoteProvider response code " + connection.responseCode)
    }

    private fun inputStreamToString(inputStream: InputStream) : String {
        var content: String
        BufferedReader(inputStream.reader()).use { reader ->
            content = reader.readText()
        }

        return content
    }
}