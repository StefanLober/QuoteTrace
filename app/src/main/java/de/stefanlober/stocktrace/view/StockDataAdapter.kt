package de.stefanlober.stocktrace.view

import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.base.BaseAdapter
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.databinding.StockRowItemBinding
import de.stefanlober.stocktrace.viewmodel.StockDataListener

class StockDataAdapter(list: List<StockData>, private val stockDataListener: StockDataListener) : BaseAdapter<StockRowItemBinding, StockData>(list) {
    override val layoutId: Int = R.layout.stock_row_item

    override fun bind(binding: StockRowItemBinding, item: StockData) {
        binding.apply {
            stockData = item
            listener = stockDataListener
            executePendingBindings()
        }
    }
}