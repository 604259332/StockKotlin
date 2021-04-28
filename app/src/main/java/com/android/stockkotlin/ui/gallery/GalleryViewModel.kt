package com.android.stockkotlin.ui.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.data.*
import com.android.stockkotlin.VolleySingletion
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var fundDao: FundDao
    val testurl = "https://hq.sinajs.cn/list="
    init {
        fundDao = AppDatabase.getDatabase(application).fundDao()
    }

    private val _funds = MutableLiveData<List<Fund>>().apply {
        Log.d("zhihai.yu","Fund apply")

        value = listOf<Fund>(
            Fund("sz002230","test Name")
        )
    }
    val funds:LiveData<List<Fund>>
        get() {
            return _funds
        }

    fun fetchData() {
        Log.d("zhihai dao", fundDao.getAll().toString())
        var fundlist: MutableList<Fund> = ArrayList<Fund>()
        var result: List<String>
        for (localfund in fundDao.getAll()) {
            Log.d("zhihai", "${testurl + localfund.stockid}")
            var stringRequest: StringRequest =
                StringRequest(testurl + localfund.stockid, Response.Listener<String> {
                    Log.d("zhihai", it)
                    if (it.contains("FAILED", false)) {
                        return@Listener
                    }
                    result = it.split(",")

                    var fund = Fund().apply {
                        stockid = localfund.stockid
                        name =
                            result[0].filter { c -> c.toString().matches(Regex("[\u4e00-\u9fa5]")) }
                        close = result[2].toFloat()
                        price = result[3].toFloat()
                        num = localfund.num
                        myprice = localfund.myprice

                    }

                    //test
                    repeat(1){
                        fundlist.add(fund)
                    }

                    _funds.value = fundlist
                }, Response.ErrorListener {
                    Log.d("zhihai.yu", it.toString())
                })
            VolleySingletion.requestQueue.add(stringRequest)
        }
        VolleySingletion.requestQueue.addRequestFinishedListener<String> {

        }
    }

    fun deleteByStockId(stockId:String){

        fundDao.deleteByStockId(stockId)

        (_funds.value as MutableList<Fund>).removeIf{
            stockId ==it.stockid
        }
        _funds.value =_funds.value
    }

}