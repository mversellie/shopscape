package pro.informatiq.shopscape.data

import pro.informatiq.shopscape.database.entities.RequestEntity
import java.util.*

data class Request(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val description: String = "",
    val entityId: UUID = UUID.randomUUID(),
){
    fun toEntity(): RequestEntity {
        return RequestEntity(
            id = id,
            name = name,
            description = description,
            entityId = entityId
        )
    }
}