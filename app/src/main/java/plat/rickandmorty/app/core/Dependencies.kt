package plat.rickandmorty.app.core

import android.content.Context
import androidx.room.Room
import plat.rickandmorty.app.core.data.AppDatabase


object Dependencies  {
    lateinit var database: AppDatabase

    fun buildDatabase(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "uvg.db"
        ).build()
    }
}
