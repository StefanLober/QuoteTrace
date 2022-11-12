package de.stefanlober.quotetrace.dao

import androidx.room.*
import de.stefanlober.quotetrace.data.StockEntity

@Dao
interface StockEntityDao {
    @Query("SELECT * FROM stockentity")
    fun getAll(): List<StockEntity>

//    @Query("SELECT * FROM stockdata WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<StockData>
//
//    @Query("SELECT * FROM stockdata WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): StockData

    @Insert
    fun insert(vararg stockEntity: StockEntity)

//    @Insert
//    fun insertAll(vararg stockDataList: StockData)

    @Delete
    fun delete(user: StockEntity)
}