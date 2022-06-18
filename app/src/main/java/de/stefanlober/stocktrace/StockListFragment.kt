package de.stefanlober.stocktrace

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.databinding.FragmentStockListBinding
import de.stefanlober.stocktrace.dataproviders.GoogleDataProvider
import java.util.concurrent.Callable


class StockListFragment : Fragment() {
    private var _binding: FragmentStockListBinding? = null

    private val binding get() = _binding!!

    private lateinit var stockDataList: List<StockData>
    private val taskRunner = TaskRunner()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStockListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_StockListFragment_to_AddStockFragment)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.stock_data_view)

        binding.fabUpdate.setOnClickListener {
            updateStockEntries(recyclerView)
        }

        stockDataList = listOf(
            StockData(1, "ETR:1u1"),
            StockData(2, "ETR:SAP"),
            StockData(3, "ETR:AMD")
        )

        val stockDataAdapter = StockDataAdapter(stockDataList, ::adapterOnEditClick, ::adapterOnDeleteClick)
        recyclerView.adapter = stockDataAdapter
        updateStockEntries(recyclerView)
    }

    private fun updateStockEntries(recyclerView: RecyclerView) {
        fun updateStockEntry(stockData: StockData?) {
            recyclerView.adapter?.notifyItemChanged(stockDataList.indexOf(stockData))
        }

        for (stockData in stockDataList) {
            taskRunner.executeAsync(StockDataUpdateCallable(stockData), ::updateStockEntry)
        }
    }

    private fun adapterOnEditClick(stockData: StockData) {
        val bundle = Bundle()
        bundle.putParcelable("StockData", stockData)
        findNavController().navigate(R.id.action_StockListFragment_to_EditStockFragment, bundle)
    }

    private fun adapterOnDeleteClick(stockData: StockData) {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class StockDataUpdateCallable(private val stockData: StockData) : Callable<StockData> {
    override fun call(): StockData {
        val dataProvider = GoogleDataProvider()

        try {
            stockData.stockQuote = dataProvider.getStockQuote(stockData.symbol)
        }
        catch(ex: Exception) {
            Log.println(Log.ERROR, "getStockQuote", ex.toString())
        }
        return stockData
    }
}

