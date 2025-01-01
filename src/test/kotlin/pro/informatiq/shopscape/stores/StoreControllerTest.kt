// StoreControllerTest.kt
package pro.informatiq.shopscape.controller
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.stores.StoreController
import pro.informatiq.shopscape.stores.StoreService
import java.util.*
class StoreControllerTest {
 private val storeService = mockk<StoreService>()
 private val mockMvc = MockMvcBuilders.standaloneSetup(StoreController(storeService)).build()
 private val objectMapper = ObjectMapper()

 @Test
 fun `should create a new store`() {
  val objectMapper = ObjectMapper()
  val newStore = Store(
   id = UUID.randomUUID(),
   streetAddress = "123 Main St",
   city = "Anytown",
   state = "CA",
   zipCode = "91234",
   phoneNumber = "555-123-4567",
   name = "My Store"
  )
  every { storeService.createStore(any(), any(), any(), any(), any(), any(), any()) } returns Unit
  mockMvc.perform(
   MockMvcRequestBuilders.post("/api/stores")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(newStore))
  )
   .andExpect(MockMvcResultMatchers.status().isCreated)
 }

 @Test
 fun `should get all stores`() {
  var store1 = Store(
   id = UUID.randomUUID(),
   streetAddress = "123 Main St",
   city = "Anytown",
   state = "CA",
   zipCode = "91234",
   phoneNumber = "555-123-4567",
   name = "Store 1"
  )
  var store2 = Store(
   id = UUID.randomUUID(),
   streetAddress = "456 Oak Ave",
   city = "Springfield",
   state = "IL",
   zipCode = "62701",
   phoneNumber = "123-456-7890",
   name = "Store 2"
  )


  val allStores = listOf(store1, store2)
  allStores.forEach { it.inflateRequestsAndIssues() }

  var equipment1 = Equipment(
   serialNumber = "EQ12345",
   description = "Hammer",
   modelNumber = "HM-100",
   id = UUID.randomUUID(),
   name = "Hammer"
  )
  var equipment2 = Equipment(
   serialNumber = "EQ67890",
   description = "Saw",
   modelNumber = "SW-200",
   id = UUID.randomUUID(),
   name = "Saw"
  )
  var equipment3 = Equipment(
   serialNumber = "EQ11111",
   description = "Drill",
   modelNumber = "DR-300",
   id = UUID.randomUUID(),
   name = "Drill"
  )

  equipment3.inflateRequestsAndIssues()
  equipment2.inflateRequestsAndIssues()
  equipment1.inflateRequestsAndIssues()

  store1.equipment = listOf(equipment1, equipment2).toMutableList()
  store2.equipment = listOf(equipment3).toMutableList()
  every { storeService.getAllStoresWithEquipment() } returns listOf(store1, store2)

  mockMvc.perform(MockMvcRequestBuilders.get("/api/stores"))
   .andExpect(MockMvcResultMatchers.status().isOk)
   .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mapOf("stores" to listOf(store1, store2)))))
 }

 @Test
 fun `should get a specific store by ID`() {
  val store = Store(
   id = UUID.randomUUID(),
   streetAddress = "456 Oak Ave",
   city = "Springfield",
   state = "IL",
   zipCode = "62701",
   phoneNumber = "123-456-7890",
   name = "Store 2"
  )
  every { storeService.getStoreById(any()) } returns store

  mockMvc.perform(
   MockMvcRequestBuilders.get("/api/stores/{id}", store.id)
  )
   .andExpect(MockMvcResultMatchers.status().isOk)
   .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(store)))
 }

 @Test
 fun `should return 404 when store not found`() {
  val nonExistentId = UUID.randomUUID()
  every { storeService.getStoreById(nonExistentId) } returns null

  mockMvc.perform(
   MockMvcRequestBuilders.get("/api/stores/{id}", nonExistentId)
  )
   .andExpect(MockMvcResultMatchers.status().isNotFound)
 }


}