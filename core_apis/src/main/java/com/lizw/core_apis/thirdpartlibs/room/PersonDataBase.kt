package com.lizw.core_apis.thirdpartlibs.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


/**
 * Created by Li Zongwei on 2021/3/30.
 */
@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDataBase : RoomDatabase() {


    abstract fun getPersonDao(): PersonDao

    companion object {
        private const val DATABASE_NAME = "person.db"
        private lateinit var mPersonDataBase: PersonDataBase

        private val mMigration1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                Log.d("PersonDataBase", "升级")
            }
        }

        fun getInstance(context: Context): PersonDataBase {
            if (::mPersonDataBase.isInitialized.not()) {
                mPersonDataBase = databaseBuilder(
                    context.applicationContext,
                    PersonDataBase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(mMigration1_2)
                    // fallbackToDestructiveMigration() 会删除数据库并重建，所有数据丢失。
                    // .fallbackToDestructiveMigration()
                    .build()
            }
            return mPersonDataBase
        }
    }
}
