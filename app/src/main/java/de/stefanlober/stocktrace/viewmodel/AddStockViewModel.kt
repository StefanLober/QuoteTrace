package de.stefanlober.stocktrace.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import de.stefanlober.stocktrace.data.StockEntity
import de.stefanlober.stocktrace.db.AppDatabase
import de.stefanlober.stocktrace.internal.EmptyEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStockViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val tag: String = StockListViewModel::class.java.simpleName

    private val stockEntityDao = Room.databaseBuilder(application, AppDatabase::class.java, "database-name").build().stockEntityDao()

    val symbol: MutableLiveData<String> = MutableLiveData<String>("NASDAQ:AMD")

    val onFinished = MutableLiveData<EmptyEvent>()

    fun add() {
        viewModelScope.launch {
            symbol.value?.let {
                addFlow(it)
                    .onStart {
                    }.catch { err ->
                        Log.println(Log.ERROR, tag, err.toString())
                    }
                    .collect {
                        onFinished.value = EmptyEvent()
                    }
            }
        }
    }

    private fun addFlow(symbol: String) = flow {
        val stockEntity = StockEntity(0, symbol)
        stockEntityDao.insert(stockEntity)

        emit(stockEntity)
    }.flowOn(Dispatchers.IO)
}