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
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.stores.StoreController
import pro.informatiq.shopscape.stores.StoreService
import java.util.*
class StoreControllerTest {
 private val storeService = mockk<StoreService>()
 private val mockMvc = MockMvcBuilders.standaloneSetup(StoreController(storeService)).build()
 @Test
 fun `should create a new store`() {
  val objectMapper = ObjectMapper()
  val newStore = Store(
   idIn = UUID.randomUUID(),
   streetAddress = "123 Main St",
   city = "Anytown",
   state = "CA",
   zipCode = "91234",
   phoneNumber = "555-123-4567",
   nameIn = "My Store"
  )
  every { storeService.createStore(any(), any(), any(), any(), any(), any(), any()) } returns Unit
  mockMvc.perform(
   MockMvcRequestBuilders.post("/stores")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(newStore))
  )
   .andExpect(MockMvcResultMatchers.status().isCreated)
 }
}