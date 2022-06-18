package de.stefanlober.stocktrace

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.stefanlober.stocktrace.dataproviders.GoogleDataProvider

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GoogleDataProviderTest {
    @Test
    fun getSAPQuote() {
        val dataProvider = GoogleDataProvider()
        val stockQuote = dataProvider.getStockQuote("ETR:SAP")
        assertEquals(9900, stockQuote.hundredthValue)
        assertEquals("EUR", stockQuote.currency)
    }
}