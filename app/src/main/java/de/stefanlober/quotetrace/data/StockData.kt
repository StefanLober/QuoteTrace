package de.stefanlober.quotetrace.data

import android.icu.text.NumberFormat
import android.icu.util.Currency
import de.stefanlober.quotetrace.base.IListAdapterItem
import java.math.BigDecimal
import java.util.*

data class StockData(
    var stockEntity: StockEntity,
    var stockQuote: StockQuote) : IListAdapterItem {
    constructor(stockEntity: StockEntity) : this(stockEntity, StockQuote("", BigDecimal(-1), "", false))

    override val id: Long
        get() = stockEntity.id

    val valueText: String
        get()
        {
            try {
                val language = "EN"
                val country = "US"
                val variant = ""
                val currencyFormat = Locale(language, country, variant).let {
                    NumberFormat.getCurrencyInstance(it).apply {
                        currency = Currency.getInstance(stockQuote.currency)
                    }
                }

                currencyFormat.setMinimumFractionDigits(1);

                return currencyFormat.format(stockQuote.amount)
            }
            catch(ex: Exception) {
                return ""
            }
        }
}

