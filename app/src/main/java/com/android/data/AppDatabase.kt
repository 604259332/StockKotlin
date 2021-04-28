package com.android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Stock::class,Fund::class), version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun stockDao(): StockDao
    abstract fun fundDao():FundDao


    companion object{
        val DB_NAME="Stockdatabase.db"

        @JvmStatic
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                //注释3处
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                    DB_NAME).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }



}