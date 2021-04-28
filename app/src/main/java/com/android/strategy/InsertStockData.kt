package com.android.strategy

import android.content.Context
import android.util.Log
import com.android.data.AppDatabase
import com.android.data.Stock
import com.android.strategy.Insert

class InsertStockData: Insert {

    override fun insertData(context: Context,stockid: String, myprice: Float, num: Int) {
        var stock: Stock = Stock(stockid)
        Log.d("zhihai",stock.toString())
        AppDatabase.getDatabase(context).stockDao().insertStock(stock)

    }
}