package pro.informatiq.shopscape.equipment

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.data.EquipmentRelationshipRequest
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import pro.informatiq.shopscape.database.entities.EquipmentRelationshipEntity
import pro.informatiq.shopscape.database.entities.StoreEntity
import pro.informatiq.shopscape.database.repositories.EquipmentRelationshipRepository
import pro.informatiq.shopscape.database.repositories.EquipmentRepository
import pro.informatiq.shopscape.database.repositories.MainEntityRepository
import pro.informatiq.shopscape.database.repositories.StoreRepository
import pro.informatiq.shopscape.entities.EntityTypeEnum
import java.util.*
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EquipmentControllerIntegrationTest {
 @Autowired
 private lateinit var mockMvc: MockMvc
 @Autowired
 private lateinit var objectMapper: ObjectMapper
 @Autowired
 private lateinit var mainEntityRepository: MainEntityRepository
 @Autowired
 private lateinit var equipmentRepository: EquipmentRepository
 @Autowired
 private lateinit var storeRepository: StoreRepository
 @Autowired
 private lateinit var equipmentRelationshipRepository: EquipmentRelationshipRepository;
 @Test
 fun `should create new equipment - integration test`() {
  val badId = UUID.randomUUID()
  val newEquipment = Equipment(
   name = "Test Equipment",
   description = "Some description",
   modelNumber = "Model 123",
   serialNumber = "SN456",
   id = badId
  )
  mockMvc.perform(
   MockMvcRequestBuilders.post("/api/equipment")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(newEquipment))
  )
   .andExpect(MockMvcResultMatchers.status().isCreated)
  val mainEntity = mainEntityRepository.findByName("Test Equipment")
  val equipmentEntity = equipmentRepository.findBySerialNumber("SN456")
  assert(mainEntity != null)
  assert(equipmentEntity != null)
  assert(mainEntity?.entityType == EntityTypeEnum.EQUIPMENT.typeCode)
  assert(mainEntity?.id != badId)
 }

 @Test
 fun `should add equipment to store and persist relationship`() {
  // Create a new store
  val newStore = makeATempStore()
  storeRepository.save(newStore)

  // Create new equipment
  val newEquipment = makeTestEquipmentEntity()
  equipmentRepository.save(newEquipment)

  // Create relationship request
  val newRelationship = EquipmentRelationshipRequest(
   equipmentId = newEquipment.entityId,
   storeId = newStore.entityId
  )

  mockMvc.perform(
   MockMvcRequestBuilders.put("/api/equipment/relationships")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(newRelationship))
  )
   .andExpect(MockMvcResultMatchers.status().isCreated)

  // Verify relationship exists in database
  val savedRelationship = equipmentRelationshipRepository.findByEquipmentAndStore(newEquipment.entityId, newStore.entityId)
  assert(savedRelationship != null)
 }

 @Test
 fun `should create equipment and retrieve it for a store`() {
  val newStore = makeATempStore()
  storeRepository.save(newStore)

  // Create new equipment
  val newEquipment = makeTestEquipmentEntity()
  equipmentRepository.save(newEquipment)


  // Create the relationship
  equipmentRelationshipRepository.save(EquipmentRelationshipEntity(store=newStore.entityId, equipment = newEquipment.entityId))

  mockMvc.perform(
   MockMvcRequestBuilders.get("/api/equipment/stores/${newStore.entityId}")
  )
   .andExpect(MockMvcResultMatchers.status().isOk())
   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
   .andExpect {
    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listOf(newEquipment.toEquipmentPojo())))
   }
 }

 fun makeATempStore() : StoreEntity {
  return StoreEntity(
   entityId = UUID.randomUUID(),
   streetAddress = "123 Main St",
   city = "Anytown",
   state = "CA",
   zipCode = "90210",
   phoneNumber = "555-123-4567"
  )
 }

 fun makeTestEquipmentEntity(): EquipmentEntity{
  return EquipmentEntity(
  entityId = UUID.randomUUID(),
  serialNumber = "SN123",
  description = "Test Equipment",
  modelNumber = "Model XYZ"
  )}


}



