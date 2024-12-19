package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import java.util.*

@Repository
interface EquipmentRepository : JpaRepository<EquipmentEntity, UUID> {
    fun findBySerialNumber(serialNumber: String): EquipmentEntity?

    @Query(
        value = "SELECT e.* FROM equipment e INNER JOIN equipment_relationships er ON e.entity_id = er.equipment_id INNER JOIN stores s ON er.store_id = s.entity_id WHERE s.entity_id = :storeId",
        nativeQuery = true
    )
    fun findByStore(storeId: UUID): List<EquipmentEntity>
}