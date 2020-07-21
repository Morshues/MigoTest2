package com.morshues.migotest2.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.morshues.migotest2.db.model.Pass

@Dao
interface PassDao {

    @Query("SELECT * FROM pass WHERE id = :id")
    fun get(id: Long): LiveData<Pass>

    @Insert
    suspend fun insert(pass: Pass): Long

    @Update
    suspend fun update(pass: Pass)
}