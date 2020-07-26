package dev.ghouse.ghousedevdemoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormDao
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormEntity

@Database(entities = arrayOf(FormEntity::class), version = 3, exportSchema = false)
abstract class FormDatabase : RoomDatabase() {
    abstract fun formDao(): FormDao

    companion object {
        @Volatile
        private var INSTANCE: FormDatabase? = null

        fun getDatabase(context: Context): FormDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FormDatabase::class.java,
                    "form_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}