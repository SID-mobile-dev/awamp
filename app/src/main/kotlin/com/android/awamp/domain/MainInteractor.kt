package com.android.awamp.domain

import com.android.awamp.database.FieldDao
import com.android.awamp.database.FieldData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MainInteractor(private val dao: FieldDao) {

    fun getFields(): Flow<List<Field>> {
        return dao.getAll()
            .flowOn(Dispatchers.IO)
            .map { list ->
                list.map {
                    map(it)
                }
            }
    }

    fun getField(name: String, side: Side): Flow<Field> {
        return dao.getByNameAndSide(name, side.name)
            .flowOn(Dispatchers.IO)
            .map {
                map(it)
            }
    }

    suspend fun setFields() {
        withContext(Dispatchers.IO) {
            fieldList.forEach {
                setField(it)
            }
        }
    }

    suspend fun setField(field: Field) {
        withContext(Dispatchers.IO) {
            dao.insert(map(field))
        }
    }

    suspend fun updateField(field: Field, dose: Float?) {
        withContext(Dispatchers.IO) {
            dao.update(map(field).copy(dose = dose))
        }
    }

    private fun map(field: Field): FieldData {
        return with(field) {
            FieldData(
                name = name,
                side = side.name,
                bodyPart = bodyPart.name,
                intervalStart = intervalStart,
                intervalEnd = intervalEnd,
                dose = dose
            )
        }
    }

    private fun map(field: FieldData): Field {
        return with(field) {
            Field(
                name = name,
                side = Side.valueOf(side),
                bodyPart = BodyPart.valueOf(bodyPart),
                intervalStart = intervalStart,
                intervalEnd = intervalEnd,
                dose = dose
            )
        }
    }

    private val fieldList = listOf(
        Field(
            name = "b. brachii",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.5f,
            intervalEnd = 0.8f,
        ),
        Field(
            name = "b. brachii",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.5f,
            intervalEnd = 0.8f,
        ),
        Field(
            name = "brachialis",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "brachialis",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "brachioradialis",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "brachioradialis",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "flexor carpi rad.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor carpi rad.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor carpi uln.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor carpi uln.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "pronator quadr.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.1f,
        ),
        Field(
            name = "pronator quadr.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.1f,
        ),
        Field(
            name = "pronator teres",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "pronator teres",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "flexor dig. sup.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor dig. sup.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor dig. prof.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor dig. prof.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 0.3f,
        ),
        Field(
            name = "flexor pollicis l.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 1.0f,
        ),
        Field(
            name = "flexor pollicis l.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.3f,
            intervalEnd = 1.0f,
        ),
        Field(
            name = "abd. pollicis br.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "abd. pollicis br.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "flex. pollicis br.",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "flex. pollicis br.",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "opp. pollicis",
            side = Side.LEFT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "opp. pollicis",
            side = Side.RIGHT,
            bodyPart = BodyPart.TOP,
            intervalStart = 0.1f,
            intervalEnd = 0.5f,
        ),
        Field(
            name = "gastrocnemius",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.75f,
            intervalEnd = 6.0f,
        ),
        Field(
            name = "gastrocnemius",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.75f,
            intervalEnd = 6.0f,
        ),
        Field(
            name = "soleus",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "soleus",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "tibialis post.",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 3.0f,
        ),
        Field(
            name = "tibialis post.",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 3.0f,
        ),
        Field(
            name = "flex. dig. long. et. hall. long.",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.25f,
            intervalEnd = 3.0f,
        ),
        Field(
            name = "flex. dig. long. et. hall. long.",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.25f,
            intervalEnd = 3.0f,
        ),
        Field(
            name = "fsemitendinosus",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "fsemitendinosus",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "semimembranosus",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "semimembranosus",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "b. femoris",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "b. femoris",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "gracilis",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.75f,
            intervalEnd = 6.0f,
        ),
        Field(
            name = "gracilis",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.75f,
            intervalEnd = 6.0f,
        ),
        Field(
            name = "adductor long. et brev.",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 1.0f,
            intervalEnd = 6.0f,
        ),
        Field(
            name = "adductor long. et brev.",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 1.0f,
            intervalEnd = 6.0f,
        ),
        Field(
            name = "adductor magnus",
            side = Side.LEFT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
        Field(
            name = "adductor magnus",
            side = Side.RIGHT,
            bodyPart = BodyPart.BOTTOM,
            intervalStart = 0.5f,
            intervalEnd = 4.0f,
        ),
    )
}
