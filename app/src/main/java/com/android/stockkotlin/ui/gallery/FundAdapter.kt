package com.android.stockkotlin.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.data.Fund
import com.android.stockkotlin.R
import com.android.stockkotlin.ui.TwoTextLayout
import com.android.stockkotlin.util.digitIs1or5
import com.android.stockkotlin.util.floatTail
import kotlinx.android.synthetic.main.fund_left_item.view.*
import kotlinx.android.synthetic.main.fund_right_item.view.*

class FundAdapter(
    var fra: GalleryFragment,
    var mlist: List<Fund>,
    var leftorright: Int = 0
):
    RecyclerView.Adapter<FundAdapter.FundViewHolder>() {

    inner class FundViewHolder : RecyclerView.ViewHolder {

        lateinit var stocklayout:TwoTextLayout
        lateinit var numlayout:TwoTextLayout
        lateinit var pricelayout:TwoTextLayout
        lateinit var totallayout:TwoTextLayout
        lateinit var todaylayout:TwoTextLayout
        lateinit var percentlayout:TextView
        lateinit var v:View
        constructor(view: View , leftorright:Int) : super(view){
            v = view
            if(leftorright == 0){
                stocklayout=view.stocklayout
            }else{
                 numlayout = view.numlayout
                 pricelayout = view.pricelayout
                 totallayout = view.totallayout
                 todaylayout = view.todaylayout
                 percentlayout = view.percentlayout
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundAdapter.FundViewHolder {
        val view:View
        if(leftorright == 0){
            view= LayoutInflater.from(parent.context).inflate(R.layout.fund_left_item,parent,false)
        }else {
            view= LayoutInflater.from(parent.context).inflate(R.layout.fund_right_item,parent,false)
        }

        return FundViewHolder(view,leftorright)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun onBindViewHolder(holder: FundAdapter.FundViewHolder, position: Int) {
        val s = mlist.get(position)

        if (leftorright == 0) {
            holder.stocklayout.setAllText(s.name, s.amount.floatTail(2))
        } else {
            holder.numlayout.setAllText(s.num.toString() , s.num.toString())
            if(s.stockid.digitIs1or5()){
                holder.pricelayout.setAllText(s.price.floatTail(3), s.myprice.floatTail(3))
            }else{
                holder.pricelayout.setAllText(s.price.floatTail(2), s.myprice.floatTail(3))
            }
            holder.totallayout.setAllText(s.profit.floatTail(2), s.profitpercent.floatTail(2)+"%")
            holder.todaylayout.setAllText(s.today.floatTail(2), s.todaypercent.floatTail(2)+"%")
            holder.percentlayout.text = s.percent.floatTail(2)
        }

        holder.v.setOnLongClickListener {
            onlongclicklistener?.onLongClick(it ,position)
            true
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