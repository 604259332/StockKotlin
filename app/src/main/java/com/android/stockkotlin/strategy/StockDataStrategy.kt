package com.android.stockkotlin.strategy

import com.android.stockkotlin.data.AppDatabase
import com.android.stockkotlin.data.Fund

interface StockDataStrategy<T> {


    fun getParams(stockId:List<String>):String

    fun parseResponse(str:String):List<T>

}