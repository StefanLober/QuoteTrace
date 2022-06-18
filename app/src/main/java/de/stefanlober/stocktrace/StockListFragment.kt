package de.stefanlober.stocktrace

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.data.StockQuote
import de.stefanlober.stocktrace.databinding.FragmentStockListBinding

class StockListFragment : Fragment() {
    private var _binding: FragmentStockListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStockListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_StockListFragment_to_AddStockFragment)
        }

        var stockDataSet = listOf(
            StockData(1, "NYSE:F", StockQuote("Ford", 1143, "USD")),
            StockData(2, "ETR:E2F", StockQuote("Électricité de France", 972, "EUR")),
            StockData(3, "ETR:BMW", StockQuote("BMW", 24589, "EUR")),
            StockData(4, "NYSE:IBM", StockQuote("IBM", 14421, "USD"))
        )
        val stockDataAdapter = StockDataAdapter(stockDataSet, ::adapterOnEditClick, ::adapterOnDeleteClick)

        val recyclerView: RecyclerView = view.findViewById(R.id.stock_data_view)
        recyclerView.adapter = stockDataAdapter
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

