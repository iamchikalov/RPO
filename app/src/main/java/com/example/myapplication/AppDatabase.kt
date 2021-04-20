package com.example.parser.com.example.parser

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parser3.Item
import com.example.parser3.MyDao

@Database(version = 1,    entities = [Item::class])

abstract class AppDatabase : RoomDatabase() {
    abstract fun myDao() : MyDao

    companion object{
        @Volatile private var instance: AppDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(
                    context
                )
                    .also{ instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
        AppDatabase::class.java,"items.db")
            .build()

    }
}