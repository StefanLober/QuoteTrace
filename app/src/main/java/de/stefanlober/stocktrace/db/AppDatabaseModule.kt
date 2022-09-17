package de.stefanlober.stocktrace.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.stefanlober.stocktrace.dao.StockEntityDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StockEntityDaoModule {

    @Singleton
    @Provides
    fun provideStockEntityDao(@ApplicationContext appContext: Context): StockEntityDao {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.DB_NAME).build().stockEntityDao()
    }
}