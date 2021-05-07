package com.android.stockkotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = arrayOf(
    Stock::class,
    Fund::class), version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun stockDao(): StockDao
    abstract fun fundDao(): FundDao


    companion object{
        val DB_NAME="Stockdatabase.db"

        @JvmStatic
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                //注释3处
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    DB_NAME
                ).addCallback(callback)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance

                return instance
            }
        }

        var callback = object :RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute({
                    INSTANCE?.stockDao()?.insertAll(listOf<Stock>(
                        Stock("sh600585" ),
                        Stock("sh600150"),
                        Stock("sh600109"),
                        Stock("sz300758"),
                        Stock("sz002230"),
                        Stock("sz002926"),

                        Stock("sh601128"),
                        Stock("sz000651"),
                        Stock("sh600761"),
                        Stock("sh603298"),
                        Stock("sz159915")
                    ))

                    INSTANCE?.fundDao()?.insertAll(listOf<Fund>(
                        Fund("sh600585",55.495f,1200),
                        Fund("sz123102",100.0f,10),
                        Fund("sz127027",100.0f,10),
                        Fund("sz300758",29.127f,300),
                        Fund("sh600150",19.540f,500),
                        Fund("sh603323",5.493f,1500),
                        Fund("sz002146",7.434f,1300)
                    ))
                })
            }
        }
    }


}