package de.stefanlober.stocktrace.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.databinding.FragmentStockListBinding
import de.stefanlober.stocktrace.internal.EmptyEventObserver
import de.stefanlober.stocktrace.internal.EventObserver
import de.stefanlober.stocktrace.viewmodel.StockListViewModel

@AndroidEntryPoint
class StockListFragment : Fragment() {
    private val viewModel by viewModels<StockListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentStockListBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = this@StockListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.adapter = StockDataAdapter(listOf(), viewModel)

        viewModel.onAddStockData.observe(viewLifecycleOwner, EmptyEventObserver {
            findNavController().navigate(R.id.action_StockListFragment_to_AddStockFragment)
        })

        viewModel.onEditStockData.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, "Edit: ${it.stockQuote.name}", Toast.LENGTH_SHORT).show()
            //val bundle = Bundle()
            //bundle.putParcelable("StockEntity", it.stockEntity)
            //findNavController().navigate(R.id.action_StockListFragment_to_EditStockFragment, bundle)
        })

        viewModel.onDeleteStockData.observe(viewLifecycleOwner, EventObserver {
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.deleteStockData(it)
                    }
                }
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Delete stock entry")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
        })

        return binding.root
    }
}