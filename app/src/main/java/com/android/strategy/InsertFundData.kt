package com.android.strategy

import android.content.Context
import android.util.Log
import com.android.data.AppDatabase
import com.android.data.Fund

class InsertFundData:Insert {
    override fun insertData(context: Context, stockid: String, myprice: Float, num: Int) {

        var fund: Fund = Fund(stockid , myprice , num)
        Log.d("zhihai insert",fund.toString())
        AppDatabase.getDatabase(context).fundDao().insertFund(fund)
    }
}