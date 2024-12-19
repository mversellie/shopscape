package pro.informatiq.shopscape.equipment

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.data.EquipmentRelationshipRequest
import java.util.*
@RestController()
@RequestMapping("/api/equipment")
class EquipmentController(val equipmentService: EquipmentService) {
    @PostMapping("")
    fun createEquipment(@RequestBody equipment: Equipment): ResponseEntity<Equipment> {
        val entityId = UUID.randomUUID()
        equipmentService.createEquipment(
            name = equipment.nameIn,
            description = equipment.description ?: "",
            modelNumber = equipment.modelNumber ?: "",
            serialNumber = equipment.serialNumber ?: "",
            entityId = entityId
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(equipment.copy(idIn = entityId))
    }

    @PutMapping("/relationships")
    fun addEquipmentToStore(@RequestBody relationship: EquipmentRelationshipRequest): ResponseEntity<Unit> {
        equipmentService.addEquipmentToStore(relationship.equipmentId, relationship.storeId)
        return ResponseEntity(HttpStatus.CREATED)
    }


    @GetMapping("/stores/{storeId}")
    fun getEquipmentForStore(@PathVariable storeId: UUID): ResponseEntity<List<Equipment>> {
        val equipment = equipmentService.getEquipmentForStore(storeId)
        return ResponseEntity.ok(equipment)
    }


}