package de.stefanlober.stocktrace

import de.stefanlober.stocktrace.dataproviders.GoogleDataProvider
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GoogleDataProviderLocalTest {
    @Test
    fun getSAPQuote() {
        val dataProvider = GoogleDataProvider()
        val stockQuote = dataProvider.getStockQuote("ETR:SAP")
        assertEquals(9900, stockQuote.hundredthValue)
        assertEquals("EUR", stockQuote.currency)
    }
}