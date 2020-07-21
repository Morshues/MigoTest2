package com.morshues.migotest2.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0
//    @ColumnInfo(name = "time_zone") var timeZone: TimeZone = TimeZone.getDefault()
)