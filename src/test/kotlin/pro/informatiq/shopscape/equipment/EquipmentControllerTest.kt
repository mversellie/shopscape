package pro.informatiq.shopscape.equipment

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.data.EquipmentRelationshipRequest
import java.util.*
class EquipmentControllerTest {
 private val equipmentService = mockk<EquipmentService>()
 private val mockMvc = MockMvcBuilders.standaloneSetup(EquipmentController(equipmentService)).build()
 private val objectMapper = jacksonObjectMapper()
 @Test
 fun `should create new equipment`() {
  val newEquipment = Equipment(
   id = UUID.randomUUID(), // ID from request body
   name = "Test Equipment",
   description = "Some description",
   modelNumber = "Model 123",
   serialNumber = "SN456"
  )
  every { equipmentService.createEquipment(any(), any(), any(), any(), any()) } answers {}
  mockMvc.perform(
   MockMvcRequestBuilders.post("/api/equipment")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(newEquipment))
  )
   .andExpect(MockMvcResultMatchers.status().isCreated)
  verify { equipmentService.createEquipment(any(), any(), any(), any(), not(newEquipment.id)) }
 }

  @Test
  fun `should add equipment to store`() {
   val newRelationship = EquipmentRelationshipRequest(
    equipmentId = UUID.randomUUID(),
    storeId = UUID.randomUUID()
   )
   every { equipmentService.addEquipmentToStore(any(), any()) } answers {}
   mockMvc.perform(
    MockMvcRequestBuilders.put("/api/equipment/relationships")
     .contentType(MediaType.APPLICATION_JSON)
     .content(objectMapper.writeValueAsString(newRelationship))
   )
    .andExpect(MockMvcResultMatchers.status().isCreated)
  }

 @Test
 fun `should retrieve equipment for a store`() {
  val aName = "name"
  val storeId = UUID.randomUUID()
  val equipmentList = mutableListOf(Equipment("serial1", "desc1", "model1", storeId, name = aName))

  every { equipmentService.getEquipmentForStore(storeId) } returns equipmentList

  mockMvc.perform(
   MockMvcRequestBuilders.get("/api/equipment/stores/$storeId")
  )
   .andExpect(MockMvcResultMatchers.status().isOk())
   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
   .andExpect {
    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(equipmentList))
   }

  verify { equipmentService.getEquipmentForStore(storeId) }
 }





 }
