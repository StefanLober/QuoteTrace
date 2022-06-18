package de.stefanlober.stocktrace.dataproviders

import de.stefanlober.stocktrace.data.StockQuote

interface IDataProvider {
    fun getStockQuote(symbol: String) : StockQuote
}