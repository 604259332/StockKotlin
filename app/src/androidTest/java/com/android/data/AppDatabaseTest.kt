package com.android.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest{

    @Before
    fun createdb(){

    }

    @After
    fun closedb(){

    }

    @Test
    fun testDatabase(){
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//
//        var db = AppDatabase.getDatabase(appContext)
//        var list = listOf<Stock>(
//            Stock("1222","test Name",12.2f,12.8f,12f),
//            Stock("2223","test Name",12.2f,12.8f,12f),
//            Stock("3444","test Name",12.2f,12.8f,12f),
//            Stock("5555","test Name",12.2f,12.8f,12f)
//        )
//
//        db.stockDao().insertAll(list)

    }


}