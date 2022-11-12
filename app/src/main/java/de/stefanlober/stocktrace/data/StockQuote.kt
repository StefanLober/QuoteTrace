package de.stefanlober.quotetrace.data

import java.math.BigDecimal

data class StockQuote(val name: String, val amount: BigDecimal, val currency: String, val loaded: Boolean)