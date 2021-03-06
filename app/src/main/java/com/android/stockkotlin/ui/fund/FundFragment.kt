package com.android.stockkotlin.ui.fund

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import com.android.stockkotlin.data.Fund
import com.android.stockkotlin.ui.HeadRecyclerAdapter
import com.android.stockkotlin.ui.SimpleItemDecoration
import com.android.stockkotlin.ui.SwapScrollView
import com.android.stockkotlin.util.floatTail
import com.android.stockkotlin.util.sum
import kotlinx.android.synthetic.main.fragment_fund.*
import kotlinx.android.synthetic.main.fragment_fund_recycleviewlayout.*

class FundFragment : Fragment(){


    private lateinit var fundViewModel: FundViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fundViewModel =
            ViewModelProviders.of(this).get(FundViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_fund, container, false)

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var left_adapter = LeftFundAdapter(fundViewModel.funds.value!!)

        left_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SimpleItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter=left_adapter
        }
        left_adapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.fund_left_head, left_rv, false))


        rightScrollView.setScrollViewListener(object : SwapScrollView.ScrollViewListener {
            override fun onScrollChanged(
                scrollView: SwapScrollView?,
                x: Int,
                y: Int,
                oldx: Int,
                oldy: Int
            ) {
                if (x != 0) {
                    cardView.setCardElevation(8f);
                } else {
                    cardView.setCardElevation(0f);
                }

            }
        })

        var right_adapter = RightFundAdapter(fundViewModel.funds.value!!)

        right_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SimpleItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter=right_adapter
        }
        right_adapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.fund_right_head, right_rv, false))


        left_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    right_rv.scrollBy(dx, dy);
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        right_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    left_rv.scrollBy(dx, dy);//?????????recyclerView????????????
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })


        left_adapter.setOnLongClickListener(object :HeadRecyclerAdapter.OnLongClickListener<Fund>{

            override fun onLongClick(v: View?, position: Int, data: Fund) {
                fundViewModel.deleteByStockId(data.stockid)
            }
        })

        fundViewModel.funds.observe(viewLifecycleOwner, Observer {
            left_adapter.datas = it
            left_adapter.notifyDataSetChanged()

            setmainFundText(fundViewModel.funds.value!!)
            right_adapter.datas = it
            right_adapter.notifyDataSetChanged()
        })
        fundViewModel.fetchData()


    }

    fun setmainFundText(funds:List<Fund>){
        var kuse_f=0.0f
        kuse.setBottomText(kuse_f.floatTail(2))
        totalFund.setBottomText((funds.sum("amount") + kuse_f).floatTail(2))

        totalstock.setBottomText(funds.sum("amount").floatTail(2))

        totalprofit.setBottomText(funds.sum("profit").floatTail(2))

        var todaysumpercent = funds.sum("today")*100 / (funds.sum("amount") + kuse_f)
        todayfund.setTopText(todaysumpercent.floatTail(2)+"%")
        todayfund.setBottomText(funds.sum("today").floatTail(2))

    }


}