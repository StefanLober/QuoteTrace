package de.stefanlober.stocktrace.data

data class StockData(var stockEntity: StockEntity, var stockQuote: StockQuote) {
    constructor(stockEntity: StockEntity) : this(stockEntity, StockQuote("", 0, ""))

    var loaded: Boolean = false
}

