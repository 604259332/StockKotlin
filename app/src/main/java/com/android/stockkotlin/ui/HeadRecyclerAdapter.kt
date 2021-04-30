package com.android.stockkotlin.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class HeadRecyclerAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    val TYPE_HEAD=0
    val TYPE_NORMAL =1
    var datas:List<T>
    private var mHeaderView:View?=null

    constructor(datas: List<T>) : super() {
        this.datas = datas
    }
    var onlongclicklistener:OnLongClickListener<T>?=null
    interface OnLongClickListener<T> {
        fun onLongClick(v: View? , position: Int , data:T)
    }

    fun setOnLongClickListener(onlongclicklistener:OnLongClickListener<T>){
        this.onlongclicklistener = onlongclicklistener
    }

    abstract fun createHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder
    abstract fun onbindHolder(holder: RecyclerView.ViewHolder, realPosition:Int,data:T)


    inner class HeadViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun setHeaderView(mHeaderView:View?){
        this.mHeaderView = mHeaderView
        notifyItemInserted(0)
    }
    fun getHeaderView():View?{
        return mHeaderView
    }

    override fun getItemViewType(position: Int): Int {
        if(mHeaderView == null)
            return TYPE_NORMAL
        if(position == 0)
            return TYPE_HEAD
        return TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(mHeaderView != null && viewType == TYPE_HEAD){
            return HeadViewHolder(mHeaderView!!)
        }
        return createHolder(parent ,viewType)
    }

    override fun getItemCount(): Int {
        if(mHeaderView == null)
            return datas.size
        else
            return datas.size+1
    }

    fun getRealPosition(holder: RecyclerView.ViewHolder):Int{
        var pos = holder.layoutPosition
        if(mHeaderView == null){
            return pos
        }
        return pos - 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == TYPE_HEAD)
            return

        val realpos = getRealPosition(holder)
        val data = datas.get(realpos)
        onbindHolder(holder,realpos,data)
        if(onlongclicklistener!=null){
            holder.itemView.setOnLongClickListener {

                onlongclicklistener?.onLongClick(it , realpos , data)
                true
            }
        }
    }


}