package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import java.util.*

@Repository
interface EquipmentRepository : JpaRepository<EquipmentEntity, UUID> {
    fun findBySerialNumber(serialNumber: String): EquipmentEntity?

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Equipment(e.serialNumber,e.description,e.modelNumber,e.entityId,equipmentMainEntity.name,null,null)  FROM EquipmentEntity e " +
                "JOIN EquipmentRelationshipEntity er ON e.entityId = er.equipment " +
                "JOIN MainEntity storeEntity ON er.store = storeEntity.id " +
                "Join MainEntity equipmentMainEntity on e.entityId = equipmentMainEntity.id " +
                "WHERE er.store= :storeId",
    )
    fun findByStore(storeId: UUID): MutableList<Equipment>
}