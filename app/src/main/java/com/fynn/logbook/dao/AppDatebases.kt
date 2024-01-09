package com.fynn.logbook.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fynn.logbook.bean.ExperimentInfo

@Database(entities = [ExperimentInfo::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun experimentDao(): AppDao

    companion object {
        private val instance: AppDb? = null

        @Synchronized
        fun getDB(context: Context): AppDb {
            return instance ?: buildDB(context)
        }

        private fun buildDB(context: Context): AppDb {
            val builder = Room.databaseBuilder(
                context,
                AppDb::class.java,
                "Experiment"
            ).fallbackToDestructiveMigration()
            return builder.build()
        }
    }
}