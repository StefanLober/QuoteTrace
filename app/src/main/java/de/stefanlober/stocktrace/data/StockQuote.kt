package de.stefanlober.stocktrace.data

import java.math.BigDecimal

data class StockQuote(val name: String, val amount: BigDecimal, val currency: String, val loaded: Boolean)