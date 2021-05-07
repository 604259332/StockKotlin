package com.android.stockkotlin.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.stockkotlin.data.AppDatabase
import com.android.stockkotlin.data.Stock
import com.android.stockkotlin.VolleySingletion
import com.android.stockkotlin.strategy.StockDataStrategy
import com.android.stockkotlin.strategy.SinaStockData
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var stockDataStrategy: StockDataStrategy<Stock>
    lateinit var db:AppDatabase
    init {
        db = AppDatabase.getDatabase(application)
        stockDataStrategy = SinaStockData()
    }

    private val _stocks = MutableLiveData<List<Stock>>().apply {
        value = listOf<Stock>(
            Stock("sz002230" ),
            Stock("sh600585" ),
            Stock("sh600150")
        )
    }
    val stocks:LiveData<List<Stock>>
        get() {
           return _stocks
        }

    fun fetchData() {

        var stockidlist = db.stockDao().getAll().map { it.stockid }

        var stringRequest: StringRequest =
            StringRequest(stockDataStrategy.getParams(stockidlist), Response.Listener<String> {
                _stocks.value = stockDataStrategy.parseResponse(it)

            }, Response.ErrorListener {
            })
        VolleySingletion.requestQueue.add(stringRequest)

        VolleySingletion.requestQueue.addRequestFinishedListener<String> {

        }
    }

    fun deleteByStockId(stockId:String){

        db.stockDao().deleteByStockId(stockId)

        (_stocks.value as MutableList<Stock>).removeIf{
            stockId ==it.stockid
        }
        _stocks.value =_stocks.value
    }



}