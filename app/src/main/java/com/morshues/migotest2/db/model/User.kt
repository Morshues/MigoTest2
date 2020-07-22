package com.morshues.migotest2.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "time_zone") var timeZone: TimeZone = TimeZone.getDefault()
)