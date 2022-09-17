package de.stefanlober.stocktrace

import de.stefanlober.stocktrace.dataproviders.GoogleDataProvider
import org.junit.Test

import org.junit.Assert.*

class GoogleDataProviderLocalTest {
    @Test
    fun getSAPQuote() {
        val dataProvider = GoogleDataProvider()
        val stockQuote = dataProvider.getStockQuote("ETR:SAP")
        assertEquals(99.00, stockQuote.amount)
        assertEquals("EUR", stockQuote.currency)
    }
}