package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*
import pro.informatiq.shopscape.data.Request
import java.util.*

@Entity
@Table(name = "requests")
data class RequestEntity (
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "entity_id", nullable = false)
    val entityId: UUID,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "status")
    var status: Int = -1,

    @Column(name = "description", length = Integer.MAX_VALUE)
    var description: String = "",
)