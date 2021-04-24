package by.vasilevskiy.dota2analytics.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun voteDao(): VoteDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "voteDB"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    fun destroyDataBase() {
        INSTANCE = null
    }

}