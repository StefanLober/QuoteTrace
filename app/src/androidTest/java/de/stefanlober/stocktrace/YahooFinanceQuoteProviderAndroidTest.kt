package de.stefanlober.stocktrace

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.stefanlober.stocktrace.dataproviders.YahooFinanceQuoteProvider

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class YahooFinanceQuoteProviderAndroidTest {
    @Test
    fun getSAPQuote() {
        val dataProvider = YahooFinanceQuoteProvider()
        val stockQuote = dataProvider.getStockQuote("SAP.DE")
        assertEquals(80.00, stockQuote.amount.toDouble(), 20.0)
        assertEquals("EUR", stockQuote.currency)
    }
}