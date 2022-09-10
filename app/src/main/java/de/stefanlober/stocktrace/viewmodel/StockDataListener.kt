package de.stefanlober.stocktrace.viewmodel

import de.stefanlober.stocktrace.data.StockData

interface StockDataListener {
    fun edit(stockData: StockData)
    fun delete(stockData: StockData)
}