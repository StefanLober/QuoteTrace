/*
package de.stefanlober.stocktrace.view

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
import androidx.room.Room
import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.TaskRunner
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.data.StockEntity
import de.stefanlober.stocktrace.databinding.FragmentStockListBinding
import de.stefanlober.stocktrace.dataproviders.GoogleDataProvider
import de.stefanlober.stocktrace.db.AppDatabase

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

        binding.fabUpdate.setOnClickListener {
            updateStockEntries(binding.stockDataView)
        }

        taskRunner.executeAsync(::fillStockDataList) { updateStockEntries(binding.stockDataView) }
    }

    private fun fillStockDataList() {
        stockDataList = emptyList()

        try {
            val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "database-name").build()
            val stockEntityDao = db.stockEntityDao()
            stockDataList = stockEntityDao.getAll().map { StockData(it) }
        } catch (ex: Exception) {
            Log.println(Log.ERROR, "fillStockDataList getAll", ex.toString())
        }

        try {
            if (stockDataList.isEmpty()) {
                val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "database-name").build()
                val stockEntityDao = db.stockEntityDao()
                stockEntityDao.insert(StockEntity(0, "ETR:1u1"))
                stockEntityDao.insert(StockEntity(0, "ETR:SAP"))
                stockEntityDao.insert(StockEntity(0, "ETR:AMD"))
                stockDataList = stockEntityDao.getAll().map { StockData(it) }
            }
        } catch (ex: Exception) {
            Log.println(Log.ERROR, "fillStockDataList insert default", ex.toString())
        }
    }

    private fun updateStockEntries(recyclerView: RecyclerView) {
        fun updateStockData(stockData: StockData) {
            val dataProvider = GoogleDataProvider()

            try {
                stockData.stockQuote = dataProvider.getStockQuote(stockData.stockEntity.symbol)
                stockData.loaded = true
            }
            catch(ex: Exception) {
                Log.println(Log.ERROR, "getStockQuote", ex.toString())
            }
        }

        val stockDataAdapter = StockDataAdapter(stockDataList, ::adapterOnEditClick, ::adapterOnDeleteClick)
        recyclerView.adapter = stockDataAdapter

        for (stockData in stockDataList) {
            stockData.loaded = false
            recyclerView.adapter?.notifyItemChanged(stockDataList.indexOf(stockData))
            taskRunner.executeAsync(stockData, ::updateStockData) { recyclerView.adapter?.notifyItemChanged(stockDataList.indexOf(stockData)) }
        }
    }

    private fun adapterOnEditClick(stockData: StockData) {
        val bundle = Bundle()
        bundle.putParcelable("StockEntity", stockData.stockEntity)
        findNavController().navigate(R.id.action_StockListFragment_to_EditStockFragment, bundle)
    }

    private fun adapterOnDeleteClick(stockData: StockData) {
        fun deleteStockEntity(stockEntity: StockEntity) {
            try {
                val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "database-name").build()
                val stockEntityDao = db.stockEntityDao()
                stockEntityDao.delete(stockEntity)
            } catch (ex: Exception) {
                Log.println(Log.ERROR, "adapterOnDeleteClick", ex.toString())
            }
        }

        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    taskRunner.executeAsync(stockData.stockEntity, ::deleteStockEntity) {
                        taskRunner.executeAsync(::fillStockDataList) { updateStockEntries(binding.stockDataView) } }
                }
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Delete stock entry")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/
