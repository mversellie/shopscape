package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "entities")
data class MainEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "name")
    var name: String,

    @Column(name = "entity_type_id", nullable = false)
    var entityType: Int,
)