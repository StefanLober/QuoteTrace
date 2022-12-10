package de.stefanlober.quotetrace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.stefanlober.quotetrace.R
import de.stefanlober.quotetrace.databinding.FragmentAddStockBinding
import de.stefanlober.quotetrace.internal.EmptyEventObserver
import de.stefanlober.quotetrace.viewmodel.AddStockViewModel

@AndroidEntryPoint
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