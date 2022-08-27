package de.stefanlober.stocktrace.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import de.stefanlober.stocktrace.R
import de.stefanlober.stocktrace.databinding.ActivityMainBinding
import de.stefanlober.stocktrace.viewmodel.MainViewModel
import de.stefanlober.stocktrace.viewmodel.StockDataAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)

        binding.adapter = StockDataAdapter(listOf(), viewModel)
    }
}