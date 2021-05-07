package com.android.stockkotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import com.android.stockkotlin.data.Stock
import com.android.stockkotlin.ui.HeadRecyclerAdapter
import kotlinx.android.synthetic.main.home_left_item.view.*

class LeftStockAdapter : HeadRecyclerAdapter<Stock> {

    constructor(datas: List<Stock>) : super(datas)

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stocktv = itemView.tvlayout
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.home_left_item, parent, false)
        return StockViewHolder(view)
    }

    override fun onbindHolder(holder: RecyclerView.ViewHolder, realPosition: Int, s: Stock) {
        if (holder is StockViewHolder)
            holder.stocktv.setAllText(s.name, s.stockid.filter { it.isDigit() })
    }
}