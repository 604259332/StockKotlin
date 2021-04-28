package com.android.stockkotlin

import android.content.Context
import androidx.startup.Initializer

class AppInitializer:Initializer<Unit>{
    override fun create(context: Context) {
        VolleySingletion.initContext(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }


}