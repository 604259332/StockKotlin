package com.android.stockkotlin.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.pull_to_refresh_header.view.*


class RefreshLinearLayout : LinearLayout {


    private lateinit var waitImageView: ProgressBar
    private lateinit var tipTextView:TextView
    private var mYOffset: Float = 0f
    private var mLastY: Float = 0f
    private var mState = State.IDLE
    lateinit var mScroller:Scroller
    lateinit var mHeaderView:View
    lateinit var right_recyclerview:RecyclerView
    var mRefreshListener:OnRefreshListener? = null
    var mInitScrollY:Int=0

    enum class State{
        IDLE,
        REFRESHING,
        PULL_TO_REFRESH,
        RELEASE_TO_REFRESH,
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){

        mScroller = Scroller(context)
        setHeaderView(context);
    }

    fun setOnRefreshListener(listener:OnRefreshListener){
        mRefreshListener = listener
    }

    interface OnRefreshListener{

        fun onRefresh()
    }

    private fun setHeaderView(context: Context?) {
        mHeaderView = LayoutInflater.from(context).
            inflate(R.layout.pull_to_refresh_header,this,false)
        addView(mHeaderView)

        waitImageView = wait_circuit_image
        tipTextView = pull_to_refresh_text

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.getAction()
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            return false
        }

        when (action) {
            MotionEvent.ACTION_DOWN -> mLastY = ev.getRawY()

            MotionEvent.ACTION_MOVE -> if (isTop() && ev.getRawY() - mLastY > 0) {
                return true
            }
        }

        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(mState == State.REFRESHING){
            return true
        }
        when(event?.action){
            MotionEvent.ACTION_DOWN -> mLastY = event.rawY
            MotionEvent.ACTION_MOVE -> {
                var currentY = event.rawY
                mYOffset = currentY - mLastY
                changeScrollY(mYOffset)
                mLastY = currentY
            }
            MotionEvent.ACTION_UP->{
                val curScrollY = getScrollY()
                if (curScrollY < mInitScrollY / 4) {
                    refresh()
                } else {
                    recoverToInitState()
                }
            }
            MotionEvent.ACTION_CANCEL->recoverToInitState()

        }
        return true
    }

    private fun refresh() {

        mState = State.REFRESHING
        mScroller.startScroll(scrollX,scrollY,0,mInitScrollY / 2 - getScrollY())
        invalidate()

        changeWidgetState()
        if(mRefreshListener != null && mState == State.REFRESHING){
            mRefreshListener?.onRefresh()
        }
    }

    private fun recoverToInitState() {

        mScroller.startScroll(getScrollX(), getScrollY(),
            0, mInitScrollY - getScrollY());
        invalidate();
    }

    private fun changeScrollY(distance: Float) {
        var curY = getScrollY();
        if (distance > 0 && curY - distance + mInitScrollY/2> getPaddingTop()) {
            // 下拉过程
            scrollBy(0, -distance.toInt());
        }else if (distance < 0 && curY - distance <= mInitScrollY) {
            // 上滑过程
            scrollBy(0, -distance.toInt());
        }
        var slop = mInitScrollY / 4;
        if(curY > 0 && curY < slop){
            mState = State.RELEASE_TO_REFRESH;
        } else if (curY > 0 && curY > slop){
            mState = State.PULL_TO_REFRESH;
        }
        changeWidgetState();

    }

    private fun changeWidgetState() {
        when(mState){
            State.PULL_TO_REFRESH ->{
                tipTextView.setText(resources.getString(R.string.pulldown));
            }
            State.RELEASE_TO_REFRESH->{
                tipTextView.setText(resources.getString(R.string.release));
            }
            State.REFRESHING->{
                waitImageView.setVisibility(VISIBLE);
                tipTextView.setText(resources.getString(R.string.refreshing));

            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        right_recyclerview = right_rv
    }

    fun completeRefresh(){

        tipTextView.setText("刷新成功");
        waitImageView.setVisibility(INVISIBLE);
        mScroller.startScroll(getScrollX(), getScrollY(),
            0, mInitScrollY / 2 - getScrollY());
        invalidate();

        mState = State.IDLE
        postDelayed({ recoverToInitState() }, 400)
    }


    /**
     * @info recycler scroll to top
     */
    private fun isTop(): Boolean {

        return !right_recyclerview.canScrollVertically(-1)
    }

    override fun computeScroll() {
        super.computeScroll()
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)

        var finalHeight:Int=0
        for(child in children){
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            finalHeight += child.getMeasuredHeight();
        }
        setMeasuredDimension(width, finalHeight);

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        mInitScrollY = mHeaderView.getMeasuredHeight() + getPaddingTop();
        scrollTo(0, mInitScrollY);
    }
}