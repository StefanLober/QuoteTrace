package de.stefanlober.quotetrace

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.stefanlober.quotetrace.dao.StockEntityDao
import de.stefanlober.quotetrace.data.StockEntity
import de.stefanlober.quotetrace.data.StockQuote
import de.stefanlober.quotetrace.quoteproviders.IQuoteProvider
import de.stefanlober.quotetrace.viewmodel.StockListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class StockListViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun initList() = runTest {
        val application = Mockito.mock(Application::class.java)

        val stockEntities = mutableListOf<StockEntity>()
        val stockEntity = StockEntity(0, "SAP")
        stockEntities.add(stockEntity)

        val stockEntityDao = mock<StockEntityDao> {
            on {getAll()} doReturn stockEntities
        }

        val stockQuote = StockQuote("SAP AG", BigDecimal("99.00"), "EUR", true)

        val dataProvider = mock<IQuoteProvider> {
            on {getStockQuote("SAP")} doReturn stockQuote
        }

        val stockListViewModel = StockListViewModel(application, stockEntityDao, dataProvider, testDispatcher)
        advanceUntilIdle()

        val showProgressBarValue = stockListViewModel.showProgressBar.getOrAwaitValue()
        assert(!showProgressBarValue)

        val stockDataListValue = stockListViewModel.stockDataList.getOrAwaitValue()
        assert(stockDataListValue[0].stockEntity.symbol == stockEntity.symbol)
        assert(stockDataListValue[0].stockQuote.name == stockQuote.name)
        assert(stockDataListValue[0].stockQuote.amount == stockQuote.amount)
        assert(stockDataListValue[0].stockQuote.currency == stockQuote.currency)
    }
}