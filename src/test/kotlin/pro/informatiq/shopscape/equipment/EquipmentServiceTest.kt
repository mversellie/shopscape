package pro.informatiq.shopscape.equipment

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import pro.informatiq.shopscape.database.entities.EquipmentRelationshipEntity
import pro.informatiq.shopscape.database.repositories.EquipmentRelationshipRepository
import pro.informatiq.shopscape.database.repositories.EquipmentRepository
import pro.informatiq.shopscape.entities.MainEntityService
import pro.informatiq.shopscape.issues.IssueService
import pro.informatiq.shopscape.requests.RequestService
import java.util.*
import kotlin.test.assertEquals

class EquipmentServiceTest {
 private val equipmentRepository = mockk<EquipmentRepository>()
 private val mainEntityService = mockk<MainEntityService>()
 private val requestsService = mockk<RequestService>()
 private val issuesService = mockk<IssueService>()
 private val equipmentRelationshipRepository = mockk<EquipmentRelationshipRepository>()
 private val equipmentService = EquipmentService(equipmentRepository, mainEntityService,equipmentRelationshipRepository, issuesService,requestsService)
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
  val expectedEquipment = equipmentList.map { it.toEquipmentPojo() }.toMutableList() // Convert to Equipment objects

  every { equipmentRepository.findByStore(storeId) } returns expectedEquipment
  every { requestsService.getAllRequestsForEntities(any()) } returns listOf()
  every { issuesService.getAllIssuesWithTypesForEntities(any()) } returns listOf()
  val result = equipmentService.getEquipmentForStore(storeId)
  verify { equipmentRepository.findByStore(storeId) }
          assert(result.size == expectedEquipment.size)
  result.forEachIndexed { index, equipment ->
   assert(equipment.id == expectedEquipment[index].id)
   assert(equipment.serialNumber == expectedEquipment[index].serialNumber)
   assert(equipment.description == expectedEquipment[index].description)
   assert(equipment.modelNumber == expectedEquipment[index].modelNumber)
  }
 }

 @Test
 fun `should get equipment for store with requests and issues`() {
  val storeId = UUID.randomUUID()
  val equipId = UUID.randomUUID();
  val equipId2 = UUID.randomUUID()
  val expectedEquipment = listOf(
   Equipment(
    id = equipId,
    serialNumber = "SN1",
    description = "Desc1",
    modelNumber = "Model1",
    name = "name"
   ),
   Equipment(
    id = equipId2,
    serialNumber = "SN2",
    description = "Desc2",
    modelNumber = "Model2",
    name = "name2"
   )
  )

  every { equipmentRepository.findByStore(any()) } returns expectedEquipment.toMutableList()

  val requestsForEquipment = listOf(
   pro.informatiq.shopscape.data.Request(id = UUID.randomUUID(), entityId = expectedEquipment[0].id),
   pro.informatiq.shopscape.data.Request(id = UUID.randomUUID(), entityId = expectedEquipment[1].id)
  )
  every { requestsService.getAllRequestsForEntities(any()) } returns requestsForEquipment

  val issuesForEquipment = listOf(
   pro.informatiq.shopscape.data.Issue(id = UUID.randomUUID(), entityId = expectedEquipment[0].id),
   pro.informatiq.shopscape.data.Issue(id = UUID.randomUUID(), entityId = expectedEquipment[1].id)
  )
  every { issuesService.getAllIssuesWithTypesForEntities(any()) } returns issuesForEquipment

  val result = equipmentService.getEquipmentForStore(storeId)
  verify { equipmentRepository.findByStore(storeId) }

  assert(result.size == expectedEquipment.size)
  result.forEachIndexed { index, equipment ->
   assert(equipment.id == expectedEquipment[index].id)
   assert(equipment.serialNumber == expectedEquipment[index].serialNumber)
   assert(equipment.description == expectedEquipment[index].description)
   assert(equipment.modelNumber == expectedEquipment[index].modelNumber)
   assertEquals(equipment.requests?.size, 1)
   assertEquals(equipment.issues?.size, 1)
  }
 }


}