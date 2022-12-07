package com.imaginato.homeworkmvvm.data.local.login

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LoginUserDBData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val loginDao: LoginDao
}