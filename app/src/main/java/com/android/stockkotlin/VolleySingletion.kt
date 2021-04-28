package com.android.stockkotlin

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object VolleySingletion {

    lateinit var context:Context

    fun initContext(context: Context){
        this.context = context.applicationContext
    }

    val requestQueue:RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

}