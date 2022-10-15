package de.stefanlober.stocktrace

import de.stefanlober.stocktrace.dataproviders.YahooFinanceDataProvider
import org.junit.Test

import org.junit.Assert.*

class YahooFinanceDataProviderLocalTest {
    @Test
    fun getSAPQuote() {
        val dataProvider = YahooFinanceDataProvider()
        val stockQuote = dataProvider.getStockQuote("SAP.DE")
        assertEquals(80.00, stockQuote.amount.toDouble(), 20.0)
        assertEquals("EUR", stockQuote.currency)
    }
}