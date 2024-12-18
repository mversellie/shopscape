package pro.informatiq.shopscape.Database.entities

import jakarta.persistence.*
import pro.informatiq.shopscape.data.Issue
import java.util.*

@Entity
@Table(name = "issues")
 data class IssueEntity (
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "entity_id", nullable = false)
    val entityId: UUID,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "description", length = Integer.MAX_VALUE)
    var description: String = ""
){
    fun toIssuePojo(): Issue {
        return Issue(
            id = this.id,
            name = this.name,
            description = this.description,
            entityId = this.entityId
        )
    }
}