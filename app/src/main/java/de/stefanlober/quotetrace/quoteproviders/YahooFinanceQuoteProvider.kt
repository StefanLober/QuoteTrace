package de.stefanlober.quotetrace.quoteproviders

import de.stefanlober.quotetrace.data.StockQuote
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject


class YahooFinanceQuoteProvider @Inject constructor() : IQuoteProvider {
    override fun getStockQuote(symbol: String): StockQuote {
        val stock: Stock = YahooFinance.get(symbol)

        return StockQuote(stock.name, stock.quote.price, stock.currency, true)
    }
}