package com.android.stockkotlin.strategy

import android.content.Context
import android.util.Log
import com.android.stockkotlin.data.AppDatabase
import com.android.stockkotlin.data.Fund

class InsertFundData: Insert {
    override fun insertData(context: Context, stockid: String, myprice: Float, num: Int) {

        var fund: Fund =
            Fund(stockid, myprice, num)
        AppDatabase.getDatabase(context).fundDao().insertFund(fund)
    }
}