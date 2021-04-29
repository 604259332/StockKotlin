package com.android.stockkotlin.strategy

import android.content.Context
import android.util.Log
import com.android.stockkotlin.data.AppDatabase
import com.android.stockkotlin.data.Stock
import com.android.stockkotlin.strategy.Insert

class InsertStockData: Insert {

    override fun insertData(context: Context,stockid: String, myprice: Float, num: Int) {
        var stock: Stock =
            Stock(stockid)
        Log.d("zhihai",stock.toString())
        AppDatabase.getDatabase(context).stockDao().insertStock(stock)

    }
}