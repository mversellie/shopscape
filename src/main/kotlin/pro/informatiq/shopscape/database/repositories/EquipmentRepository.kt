package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import java.util.*

@Repository
interface EquipmentRepository : JpaRepository<EquipmentEntity, UUID> {
}