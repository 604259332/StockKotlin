package com.android.stockkotlin.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView


class SwapScrollView : HorizontalScrollView {

    var myListener: ScrollViewListener?=null
    constructor(context: Context?) : super(context){

    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){

    }

    fun setScrollViewListener(scrollViewListener: ScrollViewListener) {
        myListener = scrollViewListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        if (myListener != null) {
            myListener?.onScrollChanged(this, x, y, oldx, oldy)
        }
    }

    interface ScrollViewListener {
        fun onScrollChanged(
            scrollView: SwapScrollView?,
            x: Int,
            y: Int,
            oldx: Int,
            oldy: Int
        )
    }
}