package com.android.stockkotlin.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SimpleItemDecoration(var context: Context,var orientation:Int): RecyclerView.ItemDecoration() {

    lateinit var mDivider:Drawable
    var mDividerHeight:Int =2
    init {
        Log.d("zhihai","orientation = " +orientation)
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw IllegalArgumentException("请输入正确的参数！");
        }

        val a: TypedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        mDivider= a.getDrawable(0)!!
        a.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        var left = parent.paddingLeft
        var right = parent.measuredWidth - parent.paddingRight
        for (child in parent.children) {

            var layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.getBottom() + layoutParams.bottomMargin;
            val bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            //if is first child, draw the top
            if(parent.getChildAdapterPosition(child) == 0){
                val top = child.getTop() + layoutParams.topMargin;
                val bottom = top + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }
//            if (mPaint != null) {
//                canvas.drawRect(left, top, right, bottom, mPaint);
//            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, mDividerHeight);
    }
}