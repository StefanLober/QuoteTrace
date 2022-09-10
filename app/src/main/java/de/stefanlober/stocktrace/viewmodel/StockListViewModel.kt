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
import de.stefanlober.stocktrace.internal.EmptyEvent
import de.stefanlober.stocktrace.internal.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListViewModel @Inject constructor(application: Application) : AndroidViewModel(application), StockDataListener {
    private val tag: String = StockListViewModel::class.java.simpleName

    private val stockEntityDao = Room.databaseBuilder(application, AppDatabase::class.java, "database-name").build().stockEntityDao()

    private val _stockDataList = MutableLiveData<List<StockData>>()
    val stockDataList: LiveData<List<StockData>> = _stockDataList

    val showProgressBar = MutableLiveData(false)

    val onAddStockData = MutableLiveData<EmptyEvent>()

    val onEditStockData = MutableLiveData<Event<StockData>>()

    val onDeleteStockData = MutableLiveData<Event<StockData>>()

    init {
        fillStockDataList()
    }

    private fun fillStockDataList() {
        viewModelScope.launch {
            fillStockDataListFlow()
                .onStart {
                    showProgressBar.postValue(true)
                }.catch { err ->
                    Log.println(Log.ERROR, tag, err.toString())
                    showProgressBar.postValue(false)
                }
                .collect { list ->
                    showProgressBar.postValue(false)
                    _stockDataList.value = list
                    updateStockQuotes()
                }
        }
    }

    private fun fillStockDataListFlow() = flow {
        var list: List<StockData>

        try {
            list = stockEntityDao.getAll().map { StockData(it) }

            if (list.isEmpty()) {
                stockEntityDao.insert(StockEntity(0, "ETR:1u1"))
                stockEntityDao.insert(StockEntity(0, "ETR:SAP"))
                stockEntityDao.insert(StockEntity(0, "ETR:AMD"))
                list = stockEntityDao.getAll().map { StockData(it) }
            }
        } catch (ex: Exception) {
            Log.println(Log.ERROR, tag, ex.toString())
            throw ex
        }

        emit(list)
    }.flowOn(Dispatchers.IO)

    fun updateStockQuotes() {
        viewModelScope.launch {
            _stockDataList.value?.let {
                updateStockQuotesFlow(it)
                    .onStart {
                        showProgressBar.postValue(true)
                    }.catch { err ->
                        Log.println(Log.ERROR, tag, err.toString())
                        showProgressBar.postValue(false)
                    }
                    .collect { list ->
                        showProgressBar.postValue(false)
                        _stockDataList.value = list
                    }
            }
        }
    }

    private fun updateStockQuotesFlow(list: List<StockData>) = flow {
        for (stockData in list)
            try {
                stockData.stockQuote = GoogleDataProvider().getStockQuote(stockData.stockEntity.symbol)
            } catch (ex: Exception) {
                Log.println(Log.ERROR, tag, ex.toString())
            }

        emit(list)
    }.flowOn(Dispatchers.IO)

    fun deleteStockData(stockData: StockData) {
        viewModelScope.launch {
            _stockDataList.value?.let {
                deleteStockDataFlow(stockData, it)
                    .onStart {
                        showProgressBar.postValue(true)
                    }.catch { err ->
                        Log.println(Log.ERROR, tag, err.toString())
                        showProgressBar.postValue(false)
                    }
                    .collect { list ->
                        showProgressBar.postValue(false)
                        _stockDataList.value = list
                    }
            }
        }
    }

    private fun deleteStockDataFlow(stockData: StockData, list: List<StockData>) = flow {
        stockEntityDao.delete(stockData.stockEntity)
        val newList: MutableList<StockData> = list.toMutableList()
        newList.remove(stockData)

        emit(newList.toList())
    }.flowOn(Dispatchers.IO)

    fun add() {
        onAddStockData.value = EmptyEvent()
    }

    override fun edit(stockData: StockData) {
        onEditStockData.value = Event(stockData)
    }

    override fun delete(stockData: StockData) {
        onDeleteStockData.value = Event(stockData)
    }
}