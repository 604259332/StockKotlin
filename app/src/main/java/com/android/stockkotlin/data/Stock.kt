package com.android.stockkotlin.data

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

    val current_dp:Float
        get() {
            checkprice()
            return price - close
        }
     val current_percentage:Float
        get() {
            checkprice()
            return (price - close)/close*100f
        }

    fun checkprice(){
        if(price == 0f){
            price = close
        }
    }
    override fun toString(): String {
        return "Stock(id=$id, stockid='$stockid', name='$name', open=$open, close=$close, price=$price)"
    }

}