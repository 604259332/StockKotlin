package com.android.stockkotlin.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.marginTop
import com.android.stockkotlin.R
import kotlinx.android.synthetic.main.two_text_linearlayout.view.*


class TwoTextLayout:LinearLayout{

    lateinit var tText:String
    lateinit var bText:String
    var tbmargin:Int = 0
    val LEFT = 0x1001
    val RIGHT = 0x1002
    val CENTER = 0x1003

    constructor(context: Context) : super(context){

    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){

        var ta:TypedArray = context.obtainStyledAttributes(attrs, R.styleable.view2Text)
        var tTextSize = ta.getInt(R.styleable.view2Text_tTextSize,15)
        var bTextSize = ta.getInt(R.styleable.view2Text_bTextSize,15)
        tText = ta.getString(R.styleable.view2Text_tText)!!
        bText = ta.getString(R.styleable.view2Text_bText)!!
        tbmargin = ta.getInt(R.styleable.view2Text_tbmargin,50)
        var mygravity = ta.getInt(R.styleable.view2Text_gravity , CENTER)
        ta.recycle()

        var view:View = LayoutInflater.from(context).inflate(R.layout.two_text_linearlayout,this)
        top_tv.text = tText
        bottom_tv.text = bText

        top_tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,tTextSize.toFloat())
        bottom_tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,bTextSize.toFloat())

        bottom_tv.layoutParams =(bottom_tv.layoutParams as LayoutParams).apply {
            topMargin = tbmargin
        }

        when(mygravity){
            LEFT -> {
                bottom_tv.gravity = Gravity.LEFT
                top_tv.gravity = Gravity.LEFT
            }
            RIGHT ->{
                bottom_tv.gravity = Gravity.RIGHT
                top_tv.gravity = Gravity.RIGHT
            }
            CENTER ->{
                bottom_tv.gravity = Gravity.CENTER_HORIZONTAL
                top_tv.gravity = Gravity.CENTER_HORIZONTAL
            }
        }

    }

    fun setTopText(str:String){
        top_tv.text = str
    }
    fun setBottomText(str:String){
        bottom_tv.text = str
    }

    fun setAllText(top:String ,bottom:String){
        top_tv.text = top
        bottom_tv.text = bottom
        invalidate()
    }

    fun setAllText(top:Float ,bottom:Float){
        top_tv.text = top.toString()
        bottom_tv.text = bottom.toString()
        invalidate()
    }


}