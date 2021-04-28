package com.android.stockkotlin.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.data.Stock
import com.android.stockkotlin.R
import com.android.stockkotlin.ui.TwoTextLayout
import kotlinx.android.synthetic.main.stockitem.view.*

class StockAdapter(var mlist: List<Stock>) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        val tvlayout: TwoTextLayout = view.tvlayout
        val price_tv:TextView=view.price_tv
        val current_percentage_tv:TextView=view.current_percentage_tv
        val current_dp_tv:TextView=view.current_dp_tv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.stockitem,parent,false)
        return StockViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val s = mlist.get(position)
        holder.tvlayout.setAllText(s.name, s.stockid.toString())
        if(s.price==0f){
            holder.price_tv.text = "${s.close}"
        }else{
            holder.price_tv.text = "${s.price}"
        }
        holder.current_dp_tv.text = "${s.current_dp}"
        holder.current_percentage_tv.text = "${s.current_percentage}"

        setTvcolor(s.current_dp.toFloat(), holder.price_tv, holder.current_dp_tv, holder.current_percentage_tv)

        holder.tvlayout.setOnLongClickListener {
            onlongclicklistener?.onLongClick(it ,position)
            true
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

    var onlongclicklistener:OnLongClickListener?=null
    interface OnLongClickListener {
        fun onLongClick(v: View? , position: Int)
    }

    fun setOnLongClickListener(onlongclicklistener:OnLongClickListener){
        this.onlongclicklistener = onlongclicklistener
    }
}