package de.stefanlober.stocktrace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.stefanlober.stocktrace.data.StockData

class StockDataAdapter(private val stockDataList: List<StockData>, private val onEditClick: (StockData) -> Unit, private val onDeleteClick: (StockData) -> Unit)
    : RecyclerView.Adapter<StockDataAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val quoteView: TextView
        val editButton : ImageButton
        val deleteButton : ImageButton
        var currentStockData: StockData? = null

        init {
            nameView = view.findViewById(R.id.name_view)
            quoteView = view.findViewById(R.id.quote_view)
            editButton = view.findViewById(R.id.edit_button)
            deleteButton = view.findViewById(R.id.delete_Button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.stock_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val stockQuote = stockDataList[position].stockQuote
        viewHolder.nameView.text = stockQuote.name
        viewHolder.quoteView.text = "${stockQuote.currency} ${stockQuote.hundredthValue / 100.0}"

        viewHolder.currentStockData = stockDataList[position]

        viewHolder.editButton.setOnClickListener {
            viewHolder.currentStockData?.let {
                onEditClick(it)
            }
        }

        viewHolder.deleteButton.setOnClickListener {
            viewHolder.currentStockData?.let {
                onDeleteClick(it)
            }
        }
    }

    override fun getItemCount() = stockDataList.size
}