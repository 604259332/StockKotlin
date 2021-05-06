package com.android.stockkotlin.ui.fund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import com.android.stockkotlin.data.Fund
import com.android.stockkotlin.ui.HeadRecyclerAdapter
import com.android.stockkotlin.ui.TwoTextLayout
import com.android.stockkotlin.util.floatTail
import kotlinx.android.synthetic.main.fund_left_item.view.*

class LeftFundAdapter: HeadRecyclerAdapter<Fund> {

    constructor(datas: List<Fund>) : super(datas)

    inner class FundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stocktv: TwoTextLayout = itemView.stocklayout
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fund_left_item,parent,false)
        return FundViewHolder(view)
    }

    override fun onbindHolder(holder: RecyclerView.ViewHolder, realPosition: Int, data: Fund) {
        if(holder is FundViewHolder)
            holder.stocktv.setAllText(data.name, data.amount.floatTail(2))
    }


}