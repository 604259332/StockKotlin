package com.android.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {

    @Query("SELECT * FROM stock")
    fun getAll(): List<Stock>

    @Query("SELECT * FROM stock WHERE id IN (:stockIds)")
    fun loadAllByIds(stockIds: IntArray): List<Stock>

    @Query("SELECT * FROM stock where id=:id")
    fun getStockById(id: Int): Stock

    @Insert
    fun insertAll(stocks: List<Stock>)

    @Insert
    fun insertStock(stock: Stock)

    @Delete
    fun delete(stock: Stock)

    @Query("Delete FROM stock WHERE stockid=:stockId")
    fun deleteByStockId(stockId:String)
}