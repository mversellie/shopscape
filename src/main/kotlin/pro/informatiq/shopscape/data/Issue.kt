package pro.informatiq.shopscape.data

import pro.informatiq.shopscape.Database.Entities.IssueEntity
import java.util.*

data class Issue(
    val id: UUID,
    val name: String = "",
    val description: String = "",
    val entityId: UUID
){
    fun toEntity(): IssueEntity {
        return IssueEntity(
            id = id,
            name = name,
            description = description,
            entityId = entityId
        )
    }
}