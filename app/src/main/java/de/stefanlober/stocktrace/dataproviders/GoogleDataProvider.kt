package de.stefanlober.stocktrace.dataproviders

import de.stefanlober.stocktrace.data.StockQuote
import org.jsoup.Jsoup
import java.net.URLEncoder

class GoogleDataProvider : IDataProvider {
    override fun getStockQuote(symbol: String): StockQuote {
        //System.setProperty("http.agent", "Chrome")
        val doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(symbol, "utf-8")).get()
        val priceDiv = doc.select("div[data-attrid=Price]")
        val quoteSpan = priceDiv[0].child(0).child(0)
        val priceText = quoteSpan.child(0).text().trim()
        val values = if(priceText.contains('.')) priceText.split("\\.") else priceText.split(",")
        val currency = quoteSpan.child(1).text().trim()

        return StockQuote("", values[0].toLong()*100 + values[1].toLong(), currency)
    }
}