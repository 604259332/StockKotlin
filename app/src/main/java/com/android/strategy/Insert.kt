package com.android.strategy

import android.content.Context
import com.android.data.AppDatabase

interface Insert {
    fun insertData(context: Context, stockid: String, myprice: Float, num: Int)
}