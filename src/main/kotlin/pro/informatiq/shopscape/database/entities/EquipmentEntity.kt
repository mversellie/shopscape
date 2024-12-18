package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*
import java.util.*
import pro.informatiq.shopscape.data.Equipment

@Entity
@Table(name = "equipment")
data class EquipmentEntity(
    @Id
    @Column(name = "entity_id",nullable = false)
    val entityId: UUID = UUID.randomUUID(),
    var serialNumber: String?,
    var description: String?,
    var modelNumber: String?,
) {
    fun toEquipmentPojo(): Equipment {
        return Equipment(
            serialNumber = this.serialNumber,
            description = this.description,
            modelNumber = this.modelNumber,
            nameIn = "",
            idIn = entityId
        )
    }
}

