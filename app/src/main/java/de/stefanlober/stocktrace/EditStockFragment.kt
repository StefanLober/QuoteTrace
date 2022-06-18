package de.stefanlober.stocktrace

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.databinding.FragmentEditStockBinding

class EditStockFragment : Fragment() {
    private var _binding: FragmentEditStockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var stockData = arguments?.getParcelable<StockData>("StockData")

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_EditStockFragment_to_StockListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}