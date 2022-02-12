package br.com.mateusr.businesscarddio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.mateusr.businesscarddio.data.daos.BusinessCardDao
import br.com.mateusr.businesscarddio.data.model.BusinessCard

@Database (entities = [BusinessCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun businessCardDao() : BusinessCardDao

    companion object{

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "BusinessCardApp.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}