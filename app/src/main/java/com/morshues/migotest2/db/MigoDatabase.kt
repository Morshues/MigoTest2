package com.morshues.migotest2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.morshues.migotest2.db.dao.AccountDao
import com.morshues.migotest2.db.dao.PassDao
import com.morshues.migotest2.db.model.Pass
import com.morshues.migotest2.db.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        User::class,
        Pass::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MigoDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun passDao(): PassDao

    companion object {
        @Volatile
        private var instance: MigoDatabase? = null

        fun getInstance(context: Context): MigoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MigoDatabase {
            return Room.databaseBuilder(context, MigoDatabase::class.java, "migo.db")
                .addCallback(seedDatabaseCallback(context))
                .build()
        }

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch(Dispatchers.IO) {
                        val accountDao = getInstance(context).accountDao()
                        val defaultUser = User()
                        accountDao.insert(defaultUser)
                    }
                }
            }
        }
    }
}