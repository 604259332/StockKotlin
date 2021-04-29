package com.android.stockkotlin.ui.gallery

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
import com.android.stockkotlin.ui.SimpleItemDecoration
import com.android.stockkotlin.ui.SwapScrollView
import com.android.stockkotlin.util.floatTail
import com.android.stockkotlin.util.sum
import kotlinx.android.synthetic.main.fragment_fund.*
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment(){


    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_fund, container, false)

        Log.d("fish","galleryViewModel1 , "+galleryViewModel.toString())
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        left_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = FundAdapter(this@GalleryFragment, galleryViewModel.funds.value!!, 0)
            addItemDecoration(SimpleItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        }
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

        right_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = FundAdapter(this@GalleryFragment, galleryViewModel.funds.value!!, 1)
            addItemDecoration(SimpleItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

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


        (left_rv.adapter as FundAdapter).setOnLongClickListener(object :FundAdapter.OnLongClickListener{
            override fun onLongClick(v: View?, position: Int) {
                val s = galleryViewModel.funds.value!!.get(position)
                galleryViewModel.deleteByStockId(s.stockid)
            }
        })

        galleryViewModel.funds.observe(viewLifecycleOwner, Observer {
            (left_rv.adapter as FundAdapter).mlist = it
            (left_rv.adapter as FundAdapter).notifyDataSetChanged()

            setmainFundText(galleryViewModel.funds.value!!)

            (right_rv.adapter as FundAdapter).mlist = it
            (right_rv.adapter as FundAdapter).notifyDataSetChanged()
        })
        galleryViewModel.fetchData()


    }

    fun setmainFundText(funds:List<Fund>){
        var kuse_f=3285.79f
        kuse.setBottomText(kuse_f.floatTail(2))
        totalFund.setBottomText((funds.sum("amount") + kuse_f).floatTail(2))

        totalstock.setBottomText(funds.sum("amount").floatTail(2))

        totalprofit.setBottomText(funds.sum("profit").floatTail(2))

        todayfund.setBottomText(funds.sum("today").floatTail(2))

    }


}