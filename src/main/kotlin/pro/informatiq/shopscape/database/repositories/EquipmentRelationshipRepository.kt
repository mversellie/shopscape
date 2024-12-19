package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.database.entities.EquipmentRelationshipEntity
import java.util.*

@Repository
interface EquipmentRelationshipRepository : JpaRepository<EquipmentRelationshipEntity, UUID> {
    fun findByEquipmentAndStore(equipment: UUID, store: UUID): EquipmentRelationshipEntity?
}