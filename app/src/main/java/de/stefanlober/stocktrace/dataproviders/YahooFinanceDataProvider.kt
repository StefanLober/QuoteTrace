package de.stefanlober.stocktrace.dataproviders

import de.stefanlober.stocktrace.data.StockQuote
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject

class YahooFinanceDataProvider @Inject constructor() : IDataProvider {
    override fun getStockQuote(symbol: String): StockQuote {
        val stock: Stock = YahooFinance.get(symbol)

        return StockQuote(stock.name, stock.quote.price, stock.currency, true)
    }
}