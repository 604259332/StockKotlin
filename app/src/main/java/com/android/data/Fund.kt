package com.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.stockkotlin.Util

@Entity
class Fund() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var stockid:String="0"

    var name:String=""
    val amount: String
        get() {
            return Util.float_T2(price * num)
        }

    var num:Int=0

    var myprice:Float=1.00f
    var price: Float=1.00f
    var close: Float=1.00f

    val profit:String
        get() {
            return Util.float_T2((price - myprice)*num)
        }
    val profitpercent:String
        get() {
            return Util.float_T2(((price-myprice)/myprice)*100)+"%"
        }

    val today:String
        get() {
            return Util.float_T2((price - close)*num)
        }
    val todaypercent:String
        get() {
            return Util.float_T2(Math.abs((price - close)/close)*100) +"%"
        }
    var percent:Float=0f

    constructor(stockid: String) : this() {
        if(!stockid.equals("")){
            this.stockid = stockid
        }
    }

    constructor(stockid: String,name: String) : this(){
        this.stockid = stockid
        this.name = name
    }

    constructor(stockid: String, myprice: Float, num: Int): this(){
        this.stockid = stockid
        this.myprice = myprice
        this.num = num
    }

    override fun toString(): String {
        return "Fund(id=$id, stockid='$stockid', name='$name', amount=$amount, num=$num, myprice=$myprice, price=$price, close=$close, profit=$profit, profitpercent=$profitpercent, today=$today, todaypercent=$todaypercent, percent=$percent)"
    }


}