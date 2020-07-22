package com.morshues.migotest2.db.model

import androidx.room.*
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Pass(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "user_id", index = true) val userId: Long,
    @ColumnInfo(name = "type") val type: PassType,
    @ColumnInfo(name = "num") val num: Int,
    @ColumnInfo(name = "insertion_time") val insertionTime: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "activation_time") var activationTime: Calendar? = null,
    @ColumnInfo(name = "expiration_time") var expirationTime: Calendar? = null
) {
    fun getState(): PassState {
        return when {
            expirationTime?.before(Calendar.getInstance()) == true -> {
                PassState.EXPIRED
            }
            activationTime?.before(Calendar.getInstance()) == true -> {
                PassState.ACTIVATED
            }
            else -> {
                PassState.ADDED
            }
        }
    }

    fun activate(timeZone: TimeZone) {
        activationTime = Calendar.getInstance()
        expirationTime = Calendar.getInstance(timeZone).apply {
            if (type == PassType.HOUR) {
                add(Calendar.HOUR_OF_DAY, num+1)
            } else {
                add(Calendar.DATE, num+1)
                set(Calendar.HOUR_OF_DAY, 0)
            }
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}

enum class PassState {
    ADDED,
    ACTIVATED,
    EXPIRED
}

enum class PassType {
    HOUR,
    DAY,
}