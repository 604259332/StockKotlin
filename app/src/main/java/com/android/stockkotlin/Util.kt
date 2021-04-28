package com.android.stockkotlin

import java.text.DecimalFormat

object Util {

    fun float_T2(number: Float): String {
        val format = DecimalFormat("0.##")
        return format.format(number)
    }

}