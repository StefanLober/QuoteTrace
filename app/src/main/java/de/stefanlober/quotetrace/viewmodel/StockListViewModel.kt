package de.stefanlober.quotetrace.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.stefanlober.quotetrace.dao.StockEntityDao
import de.stefanlober.quotetrace.data.StockData
import de.stefanlober.quotetrace.data.StockEntity
import de.stefanlober.quotetrace.dataproviders.IDataProvider
import de.stefanlober.quotetrace.internal.EmptyEvent
import de.stefanlober.quotetrace.internal.Event
import de.stefanlober.quotetrace.internal.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListViewModel @Inject constructor(
    application: Application,
    private val stockEntityDao: StockEntityDao,
    private val dataProvider: IDataProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher) : AndroidViewModel(application), StockDataListener {

    private val tag: String = StockListViewModel::class.java.simpleName

    private val _stockDataList = MutableLiveData<List<StockData>>()
    val stockDataList: LiveData<List<StockData>> = _stockDataList

    val showProgressBar = MutableLiveData(false)

    val onAddStockData = MutableLiveData<EmptyEvent>()

    val onEditStockData = MutableLiveData<Event<StockData>>()

    val onDeleteStockData = MutableLiveData<Event<StockData>>()

    init {
        Log.i(tag, "init")
        fillStockDataList()
    }

    private fun fillStockDataList() {
        viewModelScope.launch {
            fillStockDataListFlow()
                .onStart {
                    showProgressBar.postValue(true)
                }.catch { err ->
                    Log.e(tag, err.toString())
                    showProgressBar.postValue(false)
                }
                .collect { list ->
                    _stockDataList.value = list
                    updateStockQuotes()
                    showProgressBar.postValue(false)
                }
        }
    }

    private fun fillStockDataListFlow() = flow {
        var list: List<StockData>

        try {
            list = stockEntityDao.getAll().map { StockData(it) }

            if (list.isEmpty()) {
                stockEntityDao.insert(StockEntity(0, "1U1.DE"))
                stockEntityDao.insert(StockEntity(0, "SAP.DE"))
                stockEntityDao.insert(StockEntity(0, "AMD.DE"))
                list = stockEntityDao.getAll().map { StockData(it) }
            }
        } catch (ex: Exception) {
            Log.e(tag, ex.toString())
            throw ex
        }

        emit(list)
    }.flowOn(ioDispatcher)

    fun updateStockQuotes() {
        viewModelScope.launch {
            _stockDataList.value?.let {
                updateStockQuotesFlow(it)
                    .onStart {
                        showProgressBar.postValue(true)
                    }.catch { err ->
                        Log.e(tag, err.toString())
                        showProgressBar.postValue(false)
                    }
                    .collect { list ->
                        _stockDataList.value = list
                        showProgressBar.postValue(false)
                    }
            }
        }
    }

    private fun updateStockQuotesFlow(list: List<StockData>) = flow {
        for (stockData in list)
            try {
                stockData.stockQuote = dataProvider.getStockQuote(stockData.stockEntity.symbol)
            } catch (ex: Exception) {
                Log.e(tag, ex.toString())
            }

        emit(list)
    }.flowOn(ioDispatcher)

    fun deleteStockData(stockData: StockData) {
        viewModelScope.launch {
            _stockDataList.value?.let {
                deleteStockDataFlow(stockData, it)
                    .onStart {
                        showProgressBar.postValue(true)
                    }.catch { err ->
                        Log.e(tag, err.toString())
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
    }.flowOn(ioDispatcher)

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