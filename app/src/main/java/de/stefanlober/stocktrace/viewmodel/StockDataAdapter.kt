package de.stefanlober.stocktrace.viewmodel

import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.base.BaseAdapter
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.databinding.StockRowItemBinding

class StockDataAdapter(
    private val list: List<StockData>,
    private val stockDataListener: StockDataListener)
    : BaseAdapter<StockRowItemBinding, StockData>(list) {

    override val layoutId: Int = R.layout.stock_row_item

    override fun bind(binding: StockRowItemBinding, item: StockData) {
        binding.apply {
            stockData = item
            listener = stockDataListener
            executePendingBindings()
        }
    }
}

interface StockDataListener {
    fun onClicked(stockData: StockData)
}