package com.android.awamp.database

import androidx.room.Entity

@Entity(tableName = "field", primaryKeys = ["name", "side"])
data class FieldData(
    val name: String,
    val side: String,
    val bodyPart: String,
    val intervalStart: Float,
    val intervalEnd: Float,
    val dose: Float? = null,
)
