package de.stefanlober.stocktrace.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.TaskRunner
import de.stefanlober.stocktrace.data.StockEntity
import de.stefanlober.stocktrace.databinding.FragmentAddStockBinding
import de.stefanlober.stocktrace.db.AppDatabase

class AddStockFragment : Fragment() {
    private var _binding: FragmentAddStockBinding? = null

    private val taskRunner = TaskRunner()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddStockBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            try {
                fun addStockEntity(stockEntity : StockEntity) {
                    val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "database-name").build()
                    val stockEntityDao = db.stockEntityDao()
                    stockEntityDao.insert(stockEntity)
                }

                val stockEntity = StockEntity(0, binding.edittextSymbol.text.toString())
                taskRunner.executeAsync(stockEntity, ::addStockEntity) { findNavController().navigate(R.id.action_AddStockFragment_to_StockListFragment) }
            } catch (ex: Exception) {
                Log.println(Log.ERROR, "addStockEntity", ex.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}