package de.stefanlober.quotetrace.quoteproviders

import de.stefanlober.quotetrace.data.StockQuote

interface IQuoteProvider {
    fun getStockQuote(symbol: String) : StockQuote
}