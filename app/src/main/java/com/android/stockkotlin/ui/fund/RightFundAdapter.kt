package com.android.stockkotlin.ui.fund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import com.android.stockkotlin.data.Fund
import com.android.stockkotlin.ui.HeadRecyclerAdapter
import com.android.stockkotlin.util.digitIs1or5
import com.android.stockkotlin.util.floatTail
import kotlinx.android.synthetic.main.fund_right_item.view.*

class RightFundAdapter: HeadRecyclerAdapter<Fund> {

    constructor(datas: List<Fund>) : super(datas)

    inner class FundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var numlayout = itemView.numlayout
        var pricelayout = itemView.pricelayout
        var totallayout = itemView.totallayout
        var todaylayout = itemView.todaylayout
        var percentlayout = itemView.percentlayout
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.fund_right_item,parent,false)
        return FundViewHolder(view)
    }

    override fun onbindHolder(holder: RecyclerView.ViewHolder, realPosition: Int, data: Fund) {

        if (holder is RightFundAdapter.FundViewHolder) {
            holder.numlayout.setAllText(data.num.toString(), data.num.toString())
            if (data.stockid.digitIs1or5()) {
                holder.pricelayout.setAllText(data.price.floatTail(3), data.myprice.floatTail(3))
            } else {
                holder.pricelayout.setAllText(data.price.floatTail(2), data.myprice.floatTail(3))
            }
            holder.totallayout.setAllText(
                data.profit.floatTail(2),
                data.profitpercent.floatTail(2) + "%"
            )
            holder.todaylayout.setAllText(
                data.today.floatTail(2),
                data.todaypercent.floatTail(2) + "%"
            )
            holder.percentlayout.text = data.percent.floatTail(2)
        }
    }
}