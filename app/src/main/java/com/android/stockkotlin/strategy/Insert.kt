package com.android.stockkotlin.strategy

import android.content.Context
import com.android.stockkotlin.data.AppDatabase

interface Insert {
    fun insertData(context: Context, stockid: String, myprice: Float, num: Int)
}