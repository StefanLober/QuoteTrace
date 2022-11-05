package de.stefanlober.stocktrace

import de.stefanlober.stocktrace.quoteproviders.YahooFinanceQuoteProvider
import org.junit.Test

import org.junit.Assert.*

class YahooFinanceQuoteProviderLocalTest {
    @Test
    fun getSAPQuote() {
        val dataProvider = YahooFinanceQuoteProvider()
        val stockQuote = dataProvider.getStockQuote("SAP.DE")
        assertEquals(80.00, stockQuote.amount.toDouble(), 20.0)
        assertEquals("EUR", stockQuote.currency)
    }
}