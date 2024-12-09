package com.android.awamp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {

    @Query("SELECT * FROM field")
    fun getAll(): Flow<List<FieldData>>

    @Query("SELECT * FROM field WHERE name = :name")
    fun getByName(name: String): Flow<List<FieldData>>

    @Query("SELECT * FROM field WHERE name LIKE :name AND side LIKE :side")
    fun getByNameAndSide(name: String, side: String): Flow<FieldData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fieldData: List<FieldData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fieldData: FieldData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(fieldData: FieldData)

    @Delete
    fun delete(fieldData: FieldData)
}
