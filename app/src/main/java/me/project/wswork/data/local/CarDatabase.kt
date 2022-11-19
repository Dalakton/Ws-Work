package me.project.wswork.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.project.wswork.util.Constants


@Database(entities = [LeadEntity::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun CarDao(): CarDao

    companion object {

        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getInstance(context: Application): CarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}
