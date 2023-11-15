package br.com.fiap.leiapramim.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.leiapramim.model.ReadImage

@Database(entities = [ReadImage::class], version = 1)
abstract class ReadImageDB : RoomDatabase() {

    abstract fun readImageDAO(): ReadImageDAO

    companion object {

        private lateinit var instance: ReadImageDB

        fun getDB(context: Context): ReadImageDB {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        ReadImageDB::class.java,
                        "read_image_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}