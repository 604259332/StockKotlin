package com.android.stockkotlin.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.data.AppDatabase
import com.android.data.Fund
import com.android.data.Stock
import com.android.data.StockDao
import com.android.stockkotlin.VolleySingletion
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlin.collections.ArrayList

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var stockDao:StockDao
    val testurl = "https://hq.sinajs.cn/list="
    init {
        stockDao = AppDatabase.getDatabase(application).stockDao()
    }

    private val _stocks = MutableLiveData<List<Stock>>().apply {
        value = listOf<Stock>(
            Stock("sz002230","test Name"),
            Stock("sh600585","test Name"),
            Stock("sh600150","test Name")
        )
    }
    val stocks:LiveData<List<Stock>>
        get() {
           return _stocks
        }

    fun fetchData() {
        Log.d("zhihai", stockDao.getAll().toString())
        var stocklist: MutableList<Stock> = ArrayList<Stock>()
        var result: List<String>
        for (localstock in stockDao.getAll()) {
            Log.d("zhihai", "${testurl + localstock.stockid}")
            var stringRequest: StringRequest =
                StringRequest(testurl + localstock.stockid, Response.Listener<String> {
                    Log.d("zhihai", it)
                    if (it.contains("FAILED", false)) {
                        return@Listener
                    }
                    result = it.split(",")

                    var stock = Stock().apply {
                        stockid = localstock.stockid
                        name =
                            result[0].filter { c -> c.toString().matches(Regex("[\u4e00-\u9fa5]")) }
                        open = result[1].toFloat()
                        close = result[2].toFloat()
                        price = result[3].toFloat()
                    }
                    //test
                    repeat(1){
                        stocklist.add(stock)
                    }


                    _stocks.value = stocklist
                }, Response.ErrorListener {
                    Log.d("zhihai.yu", it.toString())
                })
            VolleySingletion.requestQueue.add(stringRequest)
        }
        VolleySingletion.requestQueue.addRequestFinishedListener<String> {

        }
    }

    fun deleteByStockId(stockId:String){

        stockDao.deleteByStockId(stockId)

        (_stocks.value as MutableList<Stock>).removeIf{
            stockId ==it.stockid
        }
        _stocks.value =_stocks.value
    }



}