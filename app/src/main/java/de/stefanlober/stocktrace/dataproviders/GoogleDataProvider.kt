package de.stefanlober.stocktrace.dataproviders

import de.stefanlober.stocktrace.data.StockQuote
import org.jsoup.Jsoup
import java.math.BigDecimal
import java.net.URLEncoder
import javax.inject.Inject

class GoogleDataProvider @Inject constructor() : IDataProvider {
    private val userAgent = "Mozilla/5.0 (Linux; Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Mobile Safari/537.36"

    override fun getStockQuote(symbol: String): StockQuote {
        //System.setProperty("http.agent", "Chrome")
        val doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(symbol, "utf-8")).userAgent(userAgent).get()
        val nameDiv = doc.select("div[data-attrid=title]")
        val priceDiv = doc.select("div[data-attrid=Price]")
        val quoteSpan = priceDiv[0].child(0).child(0)

        val priceText = quoteSpan.child(0).text().trim().replace(',', '.')
        val name = nameDiv.text().trim()
        val currency = quoteSpan.child(1).text().trim()

        val amount = BigDecimal(priceText)

        return StockQuote(name, amount, currency, true)
    }
}

