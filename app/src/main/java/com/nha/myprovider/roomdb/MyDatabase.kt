package com.nha.myprovider.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Database(entities = [Student::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDAO

    companion object {
        private var INSTANCE: MyDatabase? = null
        private const val DB_NAME = "mydatabase.db"

        fun getDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, DB_NAME)
                                //.allowMainThreadQueries() // Uncomment if you don't want to use RxJava or coroutines just yet (blocks UI thread)
                                .addCallback(object : Callback() {
                                    override fun onCreate(db: SupportSQLiteDatabase) {
                                        super.onCreate(db)
                                        GlobalScope.launch(Dispatchers.IO) { rePopulateDb(INSTANCE) }
                                    }
                                }).build()
                    }
                }
            }

            return INSTANCE!!
        }

        suspend fun rePopulateDb(database: MyDatabase?) {
            database?.let { db ->
                withContext(Dispatchers.IO) {
                    val studentDAO: StudentDAO = db.studentDao()
                    studentDAO.deleteAll()
                    studentDAO.insert(Student("123","DoThanhNha"))
                }
            }
        }
    }
}