package de.stefanlober.quotetrace.db

import androidx.room.Database
import androidx.room.RoomDatabase
import de.stefanlober.quotetrace.dao.StockEntityDao
import de.stefanlober.quotetrace.data.StockEntity

@Database(entities = [StockEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stockEntityDao(): StockEntityDao

    companion object {
        val DB_NAME = "quotetrace_db"
    }
}

