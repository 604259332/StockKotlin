package com.android.stockkotlin.util

import android.util.Log
import com.android.stockkotlin.data.Fund

/**
 * @info a float keep N digits after decimal point
 * @param n N digits after decimal point
 */
fun Float.floatTail(n:Int):String{

    Log.d("floatTail" , "origin float is : $this")

    var result=String.format("%.${n}f",(this))
    Log.d("floatTail" , "after float is : $this")
    return result
}

/**
 * @info 3rd c is '1' or '5'
 */
fun String.digitIs1or5():Boolean{
    if(this.length>2){
        var c = this[2]
        if (c.isDigit() && (c=='1' || c=='5')){
            return true
        }
    }
    return false
}

/**
 * @info sum of amount ,profit , today
 */
fun List<Fund>.sum(n:String):Float{

    var sum:Float =0f
    when(n){
        "amount" ->{

            for(fund in this){
                sum = sum+fund.amount
            }
        }
        "profit" ->{
            for(fund in this){
                sum = sum+fund.profit
            }
        }
        "today" ->{
            for(fund in this){
                sum = sum+fund.today
            }
        }else ->{ }
    }

    return sum
}