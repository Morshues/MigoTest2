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
}