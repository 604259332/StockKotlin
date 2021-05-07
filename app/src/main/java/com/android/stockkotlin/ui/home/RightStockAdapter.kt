package com.android.stockkotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import com.android.stockkotlin.data.Stock
import com.android.stockkotlin.ui.HeadRecyclerAdapter
import com.android.stockkotlin.util.digitIs1or5
import com.android.stockkotlin.util.floatTail
import kotlinx.android.synthetic.main.home_right_item.view.*

class RightStockAdapter: HeadRecyclerAdapter<Stock> {

    constructor(datas: List<Stock>) : super(datas)

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val price_tv =itemView.price_tv
        val current_percentage_tv =itemView.current_percentage_tv
        val current_dp_tv =itemView.current_dp_tv
        val open_tv = itemView.open_tv
        val summoney_layout = itemView.summoney_layout
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.home_right_item,parent,false)
        return StockViewHolder(view)
    }

    override fun onbindHolder(holder: RecyclerView.ViewHolder, realPosition: Int, s: Stock) {

        if (holder is StockViewHolder) {

            if(s.stockid.digitIs1or5()){
                holder.price_tv.text = s.price.floatTail(3)
                holder.current_dp_tv.text = s.current_dp.floatTail(3)
            }else{
                holder.price_tv.text = s.price.floatTail(2)
                holder.current_dp_tv.text = s.current_dp.floatTail(2)
            }

            holder.current_percentage_tv.text = s.current_percentage.floatTail(2)+"%"
            setTvcolor(s.current_dp.toFloat(), holder.price_tv, holder.current_dp_tv, holder.current_percentage_tv)
            holder.open_tv.text = s.open.floatTail(2)
            holder.summoney_layout.setTopText((s.summoney/100000000).floatTail(2))

        }
    }

    fun setTvcolor(pri: Float,vararg tvs: TextView) {

        when{
            pri > 0 -> {
                for(tv in tvs){
                    tv.setTextColor(tv.context.resources.getColor(R.color.theme_red))
                }
            }
            pri==0f -> {
                for(tv in tvs){
                    tv.setTextColor(tv.context.resources.getColor(R.color.greytext))
                }
            }
            else -> {
                for(tv in tvs){
                    tv.setTextColor(tv.context.resources.getColor(R.color.greentext))
                }
            }
        }
    }


}