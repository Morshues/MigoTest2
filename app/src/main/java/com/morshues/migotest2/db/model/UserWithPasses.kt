package com.morshues.migotest2.db.model

import androidx.room.Embedded
import androidx.room.Relation

class UserWithPasses(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "user_id",
        entity = Pass::class
    )
    val passes: List<Pass>
)