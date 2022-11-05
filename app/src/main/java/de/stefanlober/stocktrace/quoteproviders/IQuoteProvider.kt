package de.stefanlober.stocktrace.quoteproviders

import de.stefanlober.stocktrace.data.StockQuote

interface IQuoteProvider {
    fun getStockQuote(symbol: String) : StockQuote
}