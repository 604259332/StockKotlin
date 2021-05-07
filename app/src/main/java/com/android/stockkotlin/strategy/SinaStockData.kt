package com.android.stockkotlin.strategy

import android.util.Log
import com.android.stockkotlin.data.Stock

class SinaStockData():StockDataStrategy<Stock>{
    val testurl = "https://hq.sinajs.cn/list="

    /**
     * https://hq.sinajs.cn/list=sh600585,sh600150,sh600109,sz300758,
     *
     */
    override fun getParams(stockId: List<String>): String {
        var sum = ""

        for(str in stockId){
            sum += str + ","
        }
        return testurl + sum
    }

    /**
     * var hq_str_sh600585="海螺水泥,49.050,49.030,49.380,49.750,49.050,49.370,49.380,16231118,801968725.000,11584,49.370,19400,49.360,28700,49.350,13300,49.340,17100,49.330,10787,49.380,16100,49.390,90600,49.400,10500,49.410,8400,49.420,2021-05-06,15:00:00,00,";
    var hq_str_sh600150="中国船舶,16.090,15.600,17.160,17.160,16.050,17.160,0.000,79472123,1348806921.000,643291,17.160,54500,17.150,17700,17.140,28400,17.130,20200,17.120,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,2021-05-06,15:00:02,00,";
    var hq_str_sh600109="国金证券,12.300,12.090,11.640,12.350,11.610,11.640,11.650,57874456,691472956.000,36173,11.640,328300,11.630,1122100,11.620,344000,11.610,299400,11.600,9500,11.650,5400,11.660,8000,11.670,124414,11.680,37100,11.690,2021-05-06,15:00:00,00,";
    var hq_str_sz300758="七彩化学,21.230,21.210,21.460,21.580,21.200,21.440,21.460,763263,16321376.600,33700,21.440,4400,21.430,700,21.410,9200,21.400,700,21.380,1500,21.460,3000,21.470,1200,21.480,100,21.490,100,21.500,2021-05-06,15:35:00,00,D|0|0.000";
     */
    override fun parseResponse(resp: String): List<Stock> {
        if (resp.contains("FAILED", false)) {
            return emptyList()
        }
        var stocklist: MutableList<Stock> = ArrayList<Stock>()
        var stockstrlist = resp.split(";")

        for(stockstr in stockstrlist){
           var result= stockstr.split(",")
            if("".equals(stockstr.trim())){
                continue
            }
            var stock = Stock().apply {
                stockid = getstockIdFromResult(result[0])
                name = result[0].filter { c -> c.toString().matches(Regex("[\u4e00-\u9fa5]")) }
                open = result[1].toFloat()
                close = result[2].toFloat()
                if(result[3].toFloat() == 0f){
                    price = close
                }else{
                    price = result[3].toFloat()
                }

                summoney = result[9].toFloat()
            }
            stocklist.add(stock)
        }

        return stocklist
    }

    fun getstockIdFromResult(str:String):String{

        return str.subSequence(12,20).toString()
    }
}