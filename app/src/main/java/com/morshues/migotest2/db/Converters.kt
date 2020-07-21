package com.morshues.migotest2.db

import androidx.room.TypeConverter
import com.morshues.migotest2.db.model.PassType
import java.util.*

class Converters {
    @TypeConverter
    fun toPassType(value: String) = enumValueOf<PassType>(value)

    @TypeConverter
    fun fromPathType(value: PassType) = value.name

    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar?): Long? = calendar?.timeInMillis

    @TypeConverter
    fun timestampToCalendar(value: Long?): Calendar? {
        if (value == null) {
            return null
        }
        return Calendar.getInstance().apply { timeInMillis = value }
    }
}