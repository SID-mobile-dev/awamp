package com.android.awamp.domain

data class Field(
    val name: String,
    val side: Side,
    val bodyPart: BodyPart,
    val intervalStart: Float,
    val intervalEnd: Float,
    val dose: Float? = null,
)
