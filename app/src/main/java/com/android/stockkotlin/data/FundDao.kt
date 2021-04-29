package com.android.stockkotlin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FundDao {

    @Query("SELECT * FROM fund")
    fun getAll(): List<Fund>

    @Query("SELECT * FROM fund WHERE id IN (:stockIds)")
    fun loadAllByIds(stockIds: IntArray): List<Fund>

    @Query("SELECT * FROM fund where id=:id")
    fun getFundById(id: Int): Fund

    @Insert
    fun insertAll(fund: List<Fund>)

    @Insert
    fun insertFund(fund: Fund)

    @Delete
    fun delete(fund: Fund)

    @Query("Delete FROM Fund WHERE stockid=:stockId")
    fun deleteByStockId(stockId:String)
}