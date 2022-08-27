package de.stefanlober.stocktrace.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import de.stefanlober.stocktrace.data.StockData
import de.stefanlober.stocktrace.data.StockEntity
import de.stefanlober.stocktrace.dataproviders.GoogleDataProvider
import de.stefanlober.stocktrace.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application), StockDataListener {

    private val context = getApplication<Application>().applicationContext

    private val _stockDataList = MutableLiveData<List<StockData>>()
    val stockDataList: LiveData<List<StockData>> = _stockDataList

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    init {
        fillStockDataList()
    }

    private fun fillStockDataList() {
        viewModelScope.launch {
            fetchStockDataList()
                .onStart {
                    _showProgressBar.postValue(true)
                }.catch { err ->
                    _showProgressBar.postValue(false)
                }
                .collect { list ->
                    _showProgressBar.postValue(false)
                    _stockDataList.value = list
                    update()
                }
        }
    }

    private fun fetchStockDataList() = flow {
        var list: List<StockData>

        try {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
            val stockEntityDao = db.stockEntityDao()
            list = stockEntityDao.getAll().map { StockData(it) }
        } catch (ex: Exception) {
            Log.println(Log.ERROR, "fillStockDataList getAll", ex.toString())
            throw ex
        }

        try {
            if (list.isEmpty()) {
                val db = Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
                val stockEntityDao = db.stockEntityDao()
                stockEntityDao.insert(StockEntity(0, "ETR:1u1"))
                stockEntityDao.insert(StockEntity(0, "ETR:SAP"))
                stockEntityDao.insert(StockEntity(0, "ETR:AMD"))
                list = stockEntityDao.getAll().map { StockData(it) }
            }
        } catch (ex: Exception) {
            Log.println(Log.ERROR, "fillStockDataList insert default", ex.toString())
            throw ex
        }

        emit(list)
    }.flowOn(Dispatchers.IO)

    fun update() {
        viewModelScope.launch {
            _stockDataList.value?.let {
                updateStockQuotes(it)
                    .onStart {
                        _showProgressBar.postValue(true)
                    }.catch { err ->
                        _showProgressBar.postValue(false)
                    }
                    .collect { list ->
                        _showProgressBar.postValue(false)
                        _stockDataList.value = list
                    }
            }
        }
    }

    private fun updateStockQuotes(list: List<StockData>) = flow {
        for (stockData in _stockDataList.value!!)
            try {
                stockData.stockQuote = GoogleDataProvider().getStockQuote(stockData.stockEntity.symbol)
            } catch (ex: Exception) {
                Log.println(Log.ERROR, "getStockQuote", ex.toString())
            }

        emit(list)
    }.flowOn(Dispatchers.IO)

    override fun onClicked(stockData: StockData) {
        TODO("Not yet implemented")
    }
}