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
import pro.informatiq.shopscape.issues.IssueService
import pro.informatiq.shopscape.requests.RequestService

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val mainEntityService: MainEntityService,
    private val equipmentRelationshipRepository: EquipmentRelationshipRepository,
    private val issueService: IssueService,
    private val requestService: RequestService
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

    fun getEquipmentForStore(storeId: UUID): MutableList<Equipment> {
        val equipmentEntitiesMap = equipmentRepository.findByStore(storeId)
        val equipmentIds = equipmentEntitiesMap.map { it.id }
        val requestsGrouped = requestService.getAllRequestsForEntities(equipmentIds).groupBy { it.entityId }
        val issuesGrouped = issueService.getAllIssuesWithTypesForEntities(equipmentIds).groupBy { it.entityId }
        for (eq in equipmentEntitiesMap){
            eq.requests=requestsGrouped[eq.id].orEmpty()
            eq.issues=issuesGrouped[eq.id].orEmpty()
        }
        return equipmentEntitiesMap
    }


}