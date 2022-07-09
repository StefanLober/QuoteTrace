package de.stefanlober.stocktrace.db

import androidx.room.Database
import androidx.room.RoomDatabase
import de.stefanlober.stocktrace.dao.StockEntityDao
import de.stefanlober.stocktrace.data.StockEntity

@Database(entities = [StockEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockEntityDao(): StockEntityDao
}