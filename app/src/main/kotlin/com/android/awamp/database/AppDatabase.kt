package com.android.awamp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FieldData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFieldDao(): FieldDao
}
