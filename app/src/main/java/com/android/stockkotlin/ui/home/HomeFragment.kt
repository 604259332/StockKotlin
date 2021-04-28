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
import com.android.stockkotlin.ui.SimpleItemDecoration
import kotlinx.android.synthetic.main.fragment_home.view.*

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
        val recyclerView:RecyclerView = root.stocksRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val adapter = StockAdapter(homeViewModel.stocks.value!!)
        recyclerView.adapter =adapter
        recyclerView.addItemDecoration(
            SimpleItemDecoration(
                container!!.context,
                LinearLayoutManager.HORIZONTAL
            )
        )
        adapter.setOnLongClickListener(object :StockAdapter.OnLongClickListener{
            override fun onLongClick(v: View?, position: Int) {
                val stock = homeViewModel.stocks.value!!.get(position)
                homeViewModel.deleteByStockId(stock.stockid)
            }

        })

        homeViewModel.stocks.observe(viewLifecycleOwner, Observer {
            adapter.mlist = it
            adapter.notifyDataSetChanged()
        })
        homeViewModel.fetchData()

        return root
    }
}