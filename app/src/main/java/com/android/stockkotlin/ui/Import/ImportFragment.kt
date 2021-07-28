package com.android.stockkotlin.ui.Import

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.stockkotlin.R
import com.android.stockkotlin.strategy.Insert
import com.android.stockkotlin.strategy.InsertFundData
import com.android.stockkotlin.strategy.InsertStockData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_import.*

class ImportFragment : Fragment() {

    private lateinit var importViewModel: ImportViewModel

    lateinit var insertstrategy: Insert

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        importViewModel =
                ViewModelProviders.of(this).get(ImportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_import, container, false)

        importViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        return root
    }

    fun setInsertStrategy(insertstrategy: Insert){
        this.insertstrategy = insertstrategy
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.stock_rtn-> {
                    setInsertStrategy(InsertStockData())
                }
                R.id.fund_rtn -> {
                    setInsertStrategy(InsertFundData())
                }
                R.id.moni_rtn -> {
                    setInsertStrategy(InsertStockData())
                }
            }
        }
        sub_btn.setOnClickListener {

            var mynumber: Int = stock_number.text.toString().run {
                if (!this.equals(""))
                    this.toInt()
                else
                    0
            }
            var myprice: Float = stock_myprice.text.toString().run {
                if (!this.equals(""))
                    this.toFloat()
                else
                    0f
            }
            if ("".equals(stockid.text.toString().trim())) {
                return@setOnClickListener
            }
            var stock_tv: String = stockid.text.toString().trim()
            if(!::insertstrategy.isInitialized){
                setInsertStrategy(InsertStockData())
            }
            insertstrategy.insertData(
                it.context, stock_tv
                , myprice, mynumber
            )
            Snackbar.make(it, getString(R.string.addsuccess), Snackbar.LENGTH_LONG).show()
            stockid.text = Editable.Factory.getInstance().newEditable("")
        }

    }
}