package com.android.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.stockkotlin.Util

@Entity
class Stock() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var stockid:String="0"
    @ColumnInfo(name = "_name")  var name: String=""
    var open: Float=1.00f
    var close: Float=1.00f
    var price: Float=1f

    constructor(stockid: String) : this() {
        if(!stockid.equals("")){
            this.stockid = stockid
        }
    }

    constructor(stockid: String,name: String) : this(){
        this.stockid = stockid
        this.name = name
    }

    val current_dp:String
        get() {
            if(price == 0f){
                return "0.00"
            }
            return Util.float_T2(price - close)
        }
     val current_percentage:String
        get() {
            if(price == 0f){
                return "0.00%"
            }
            return Util.float_T2(Math.abs((price - close)/close)*100) +"%"
        }

    override fun toString(): String {
        return "Stock(id=$id, stockid='$stockid', name='$name', open=$open, close=$close, price=$price)"
    }

}