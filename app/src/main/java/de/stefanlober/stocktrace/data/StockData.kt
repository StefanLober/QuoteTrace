package de.stefanlober.stocktrace.data

import de.stefanlober.stocktrace.base.ListAdapterItem

data class StockData(var stockEntity: StockEntity, var stockQuote: StockQuote) : ListAdapterItem {
    constructor(stockEntity: StockEntity) : this(stockEntity, StockQuote("", 0, ""))

    override val id: Long
        get() = stockEntity.id

    val valueText: String
        get() = "${stockQuote.currency} ${stockQuote.hundredthValue / 100.0}"
}

