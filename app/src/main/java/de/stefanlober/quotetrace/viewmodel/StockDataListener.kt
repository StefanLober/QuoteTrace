package de.stefanlober.quotetrace.viewmodel

import de.stefanlober.quotetrace.data.StockData

interface StockDataListener {
    fun edit(stockData: StockData)
    fun delete(stockData: StockData)
}