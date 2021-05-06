package com.android.stockkotlin.strategy

import com.android.stockkotlin.data.AppDatabase

interface StockDataStrategy<T> {


    fun getParams(database: AppDatabase):String

    fun parseResponse(str:String):List<T>

}