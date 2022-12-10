package de.stefanlober.quotetrace.dataproviders

import de.stefanlober.quotetrace.data.StockQuote

interface IDataProvider {
    fun getStockQuote(symbol: String) : StockQuote
}