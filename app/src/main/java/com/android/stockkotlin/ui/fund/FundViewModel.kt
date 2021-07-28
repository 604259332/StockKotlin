package com.android.stockkotlin.ui.fund

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.stockkotlin.VolleySingletion
import com.android.stockkotlin.data.AppDatabase
import com.android.stockkotlin.data.Fund
import com.android.stockkotlin.data.Stock
import com.android.stockkotlin.strategy.SinaStockData
import com.android.stockkotlin.strategy.StockDataStrategy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class FundViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var stockDataStrategy: StockDataStrategy<Stock>
    lateinit var db:AppDatabase
    init {
        db = AppDatabase.getDatabase(application)
        stockDataStrategy = SinaStockData()
    }

    private val _funds = MutableLiveData<List<Fund>>().apply {
        value = listOf<Fund>(
            Fund("sz002230", "test Name")
        )
    }
    val funds:LiveData<List<Fund>>
        get() {
            return _funds
        }

    fun fetchData() {
        var funds = db.fundDao().getAll()
        var stringRequest: StringRequest =
            StringRequest(
                stockDataStrategy.getParams(funds.map { it.stockid }),
                Response.Listener<String> {
                    var stocks = stockDataStrategy.parseResponse(it)

                    for (i in funds.indices) {
                        funds.get(i).stockid = stocks.get(i).stockid
                        funds.get(i).name = stocks.get(i).name
                        funds.get(i).close = stocks.get(i).close
                        funds.get(i).price = stocks.get(i).price
                    }
                    _funds.value = funds
                },
                Response.ErrorListener {
                })
        VolleySingletion.requestQueue.add(stringRequest)

        VolleySingletion.requestQueue.addRequestFinishedListener<String> {

        }
    }

    fun deleteByStockId(stockId:String){

        db.fundDao().deleteByStockId(stockId)

        (_funds.value as MutableList<Fund>).removeIf{
            stockId ==it.stockid
        }
        _funds.value =_funds.value
    }

}