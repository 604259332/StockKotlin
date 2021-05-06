package com.android.stockkotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Fund() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var stockid:String="0"

    var name:String=""
    val amount: Float
        get() {
            checkprice()
            return price * num
        }

    var num:Int=0

    var myprice:Float=1.00f
    var price: Float=1.00f
    var close: Float=1.00f

    val profit:Float
        get() {
            checkprice()
            return (price - myprice)*num
        }
    val profitpercent:Float
        get() {
            checkprice()
            return ((price-myprice)/myprice)*100f
        }

    val today:Float
        get() {
            checkprice()
            return (price - close)*num
        }
    val todaypercent:Float
        get() {
            checkprice()
            return (price - close)/close*100f
        }
    var percent:Float=0f

    fun checkprice(){
        if(price == 0f){
            price = close
        }
    }

    override fun toString(): String {
        return "Fund(id=$id, stockid='$stockid', name='$name', num=$num, myprice=$myprice, price=$price, close=$close, percent=$percent)"
    }

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



}