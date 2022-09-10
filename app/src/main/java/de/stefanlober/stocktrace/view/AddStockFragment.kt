package de.stefanlober.stocktrace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.databinding.FragmentAddStockBinding
import de.stefanlober.stocktrace.internal.EmptyEventObserver
import de.stefanlober.stocktrace.internal.EventObserver
import de.stefanlober.stocktrace.viewmodel.AddStockViewModel

class AddStockFragment : Fragment() {
    private val viewModel by viewModels<AddStockViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentAddStockBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = this@AddStockFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.onFinished.observe(viewLifecycleOwner, EmptyEventObserver {
            findNavController().navigate(R.id.action_AddStockFragment_to_StockListFragment)
        })

        return binding.root
    }
}