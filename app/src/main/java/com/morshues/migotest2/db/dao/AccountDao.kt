package com.morshues.migotest2.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.morshues.migotest2.db.model.UserWithPasses
import com.morshues.migotest2.db.model.User

@Dao
interface AccountDao {
    @Transaction
    @Query("SELECT * FROM user LIMIT 1")
    fun getFirst(): LiveData<UserWithPasses>

    @Transaction
    @Query("SELECT * FROM user WHERE id = :id")
    fun get(id: Long): LiveData<UserWithPasses>

    @Insert
    fun insert(user: User): Long
}