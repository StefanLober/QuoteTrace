package de.stefanlober.quotetrace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.stefanlober.quotetrace.databinding.FragmentEditStockBinding
import de.stefanlober.quotetrace.viewmodel.EditStockViewModel

@AndroidEntryPoint
class EditStockFragment : Fragment() {
    private val viewModel by viewModels<EditStockViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentEditStockBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = this@EditStockFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}