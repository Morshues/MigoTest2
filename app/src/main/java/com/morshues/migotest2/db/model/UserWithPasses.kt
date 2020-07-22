package com.morshues.migotest2.db.model

import androidx.room.Embedded
import androidx.room.Relation
import java.util.*

class UserWithPasses(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "user_id",
        entity = Pass::class
    )
    val passes: List<Pass>
) {
    fun hasActivatedPass(): Boolean {
        val now = Calendar.getInstance()
        return passes.any { pass ->
            now.before(pass.expirationTime) && now.after(pass.activationTime)
        }
    }

    /**
     * Sorting rules:
     *      1. Activated -> Added > Expired
     *      2. day = 24 * hour
     */
    fun sortedPasses(): List<Pass> {
        return passes.sortedBy {
            it.num * (if (it.type == PassType.HOUR) 1 else 24)
        }.sortedBy {
            when (it.getState()) {
                PassState.ADDED -> 2
                PassState.ACTIVATED -> 1
                PassState.EXPIRED -> 3
            }
        }
    }
}