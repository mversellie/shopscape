package pro.informatiq.shopscape.equipment

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import pro.informatiq.shopscape.database.entities.EquipmentRelationshipEntity
import pro.informatiq.shopscape.database.repositories.EquipmentRelationshipRepository
import pro.informatiq.shopscape.database.repositories.EquipmentRepository
import pro.informatiq.shopscape.entities.MainEntityService
import java.util.*
class EquipmentServiceTest {
 private val equipmentRepository = mockk<EquipmentRepository>()
 private val mainEntityService = mockk<MainEntityService>()
 private val equipmentRelationshipRepository = mockk<EquipmentRelationshipRepository>()
 private val equipmentService = EquipmentService(equipmentRepository, mainEntityService,equipmentRelationshipRepository)
 @Test
 fun `should create new equipment`() {
  val entityId = UUID.randomUUID()
  val name = "Test Equipment"
  val description = "Some description"
  val modelNumber = "Model 123"
  val serialNumber = "SN456"
  every { mainEntityService.createMainEntity(any(), any(), any()) } answers {}
  val newEquipmentEntity = EquipmentEntity(entityId, serialNumber, description, modelNumber)
  every { equipmentRepository.save(any()) } returns newEquipmentEntity
  equipmentService.createEquipment(name, description, modelNumber, serialNumber, entityId)
  verify { equipmentRepository.save(EquipmentEntity(entityId, serialNumber, description, modelNumber)) }
 }
 @Test
 fun `should add equipment to store`() {
  val equipmentId = UUID.randomUUID()
  val storeId = UUID.randomUUID()
  every { equipmentRelationshipRepository.save(any()) } returns EquipmentRelationshipEntity(equipment = equipmentId, store=storeId)
  equipmentService.addEquipmentToStore(equipmentId, storeId)
  verify { equipmentRelationshipRepository.save(any()) }
 }

 @Test
 fun `should get equipment for store`() {
  val storeId = UUID.randomUUID()
  val equipmentList = listOf(
   EquipmentEntity(entityId = UUID.randomUUID(), serialNumber = "SN1", description = "Desc1", modelNumber = "Model1"),
   EquipmentEntity(entityId = UUID.randomUUID(), serialNumber = "SN2", description = "Desc2", modelNumber = "Model2")
  )
  val expectedEquipment = equipmentList.map { it.toEquipmentPojo() } // Convert to Equipment objects

  every { equipmentRepository.findByStore(storeId) } returns equipmentList
  val result = equipmentService.getEquipmentForStore(storeId)
  verify { equipmentRepository.findByStore(storeId) }
          assert(result.size == expectedEquipment.size)
  result.forEachIndexed { index, equipment ->
   assert(equipment.idIn == expectedEquipment[index].idIn)
   assert(equipment.serialNumber == expectedEquipment[index].serialNumber)
   assert(equipment.description == expectedEquipment[index].description)
   assert(equipment.modelNumber == expectedEquipment[index].modelNumber)
  }
 }


}