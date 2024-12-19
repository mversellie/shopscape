package pro.informatiq.shopscape.equipment

import java.util.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import pro.informatiq.shopscape.database.entities.EquipmentRelationshipEntity
import pro.informatiq.shopscape.database.repositories.EquipmentRelationshipRepository
import pro.informatiq.shopscape.database.repositories.EquipmentRepository
import pro.informatiq.shopscape.entities.EntityTypeEnum
import pro.informatiq.shopscape.entities.MainEntityService

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val mainEntityService: MainEntityService,
    private val equipmentRelationshipRepository: EquipmentRelationshipRepository
) {
    @Transactional
    fun createEquipment(
        name: String,
        description: String,
        modelNumber: String,
        serialNumber: String,
        entityId: UUID
    ) {
        mainEntityService.createMainEntity(name, EntityTypeEnum.EQUIPMENT.typeCode, entityId)
        val newEquipment = EquipmentEntity(
            entityId = entityId,
            serialNumber = serialNumber,
            description = description,
            modelNumber = modelNumber
        )
        equipmentRepository.save(newEquipment)
    }

    fun addEquipmentToStore(equipmentIdIn: UUID, storeIdIn: UUID) {
        val newRelationship = EquipmentRelationshipEntity(
            equipment = equipmentIdIn,
            store = storeIdIn
        )
        equipmentRelationshipRepository.save(newRelationship)
    }


    fun getEquipmentForStore(storeId: UUID): List<Equipment> {
        return equipmentRepository.findByStore(storeId).map { it.toEquipmentPojo() }
    }


}