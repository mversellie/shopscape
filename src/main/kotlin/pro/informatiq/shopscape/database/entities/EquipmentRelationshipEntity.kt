package pro.informatiq.shopscape.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "equipment_relationships")
data class EquipmentRelationshipEntity (
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "equipment_id")
    var equipment: UUID,

    @Column(name = "store_id")
    var store: UUID
)