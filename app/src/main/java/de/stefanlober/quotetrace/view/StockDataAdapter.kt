package de.stefanlober.quotetrace.view

import de.stefanlober.quotetrace.R
import de.stefanlober.quotetrace.base.BaseAdapter
import de.stefanlober.quotetrace.data.StockData
import de.stefanlober.quotetrace.databinding.StockRowItemBinding
import de.stefanlober.quotetrace.viewmodel.StockDataListener

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