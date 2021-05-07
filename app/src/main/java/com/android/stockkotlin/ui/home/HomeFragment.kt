package com.android.stockkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.stockkotlin.R
import com.android.stockkotlin.data.Stock
import com.android.stockkotlin.ui.HeadRecyclerAdapter
import com.android.stockkotlin.ui.SimpleItemDecoration
import com.android.stockkotlin.ui.SwapScrollView
import kotlinx.android.synthetic.main.fragment_home.cardView
import kotlinx.android.synthetic.main.fragment_home.left_rv
import kotlinx.android.synthetic.main.fragment_home.right_rv
import kotlinx.android.synthetic.main.fragment_home.rightScrollView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var left_adapter = LeftStockAdapter(homeViewModel.stocks.value!!)
        left_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SimpleItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter=left_adapter
        }
        left_adapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.home_left_head, left_rv, false))
        left_adapter.setOnLongClickListener(object : HeadRecyclerAdapter.OnLongClickListener<Stock>{

            override fun onLongClick(v: View?, position: Int, data: Stock) {
                homeViewModel.deleteByStockId(data.stockid)
            }
        })
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

        var right_adapter = RightStockAdapter(homeViewModel.stocks.value!!)

        right_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SimpleItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter=right_adapter
        }
        right_adapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.home_right_head, right_rv, false))
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
                    left_rv.scrollBy(dx, dy);//使左边recyclerView进行联动
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        homeViewModel.stocks.observe(viewLifecycleOwner, Observer {
            left_adapter.datas = it
            left_adapter.notifyDataSetChanged()

            right_adapter.datas = it
            right_adapter.notifyDataSetChanged()
        })
        homeViewModel.fetchData()
    }
}