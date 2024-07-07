package com.example.wordlemix.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Player::class],
    version = 7,
    exportSchema = false
)
abstract class PlayerDatabase: RoomDatabase() {
    abstract fun playerDao(): PlayerDAO

    companion object{
        @Volatile
        private var instance: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase{
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, PlayerDatabase::class.java, "player_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }
}
