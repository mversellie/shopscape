package pro.informatiq.shopscape.controller

// StoreControllerIntegrationTest.kt
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
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.database.repositories.MainEntityRepository
import pro.informatiq.shopscape.database.repositories.StoreRepository
import java.util.*
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StoreControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    @Autowired
    private lateinit var mainEntityRepository: MainEntityRepository
    @Autowired
    private lateinit var storeRepository: StoreRepository
    @Test
    fun `should create a new store - integration test`() {
        val newStore = Store(
            idIn = UUID.randomUUID(),
            streetAddress = "123 Main St",
            city = "Anytown",
            state = "CA",
            zipCode = "91234",
            phoneNumber = "555-123-4567",
            nameIn = "My Store"
        )
        mockMvc.perform(
            MockMvcRequestBuilders.post("/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStore))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
        val storedEntity = mainEntityRepository.findByName("My Store")
        val storedStore = storeRepository.findByStreetAddress(newStore.streetAddress)
        assert(storedEntity != null)
        assert(storedStore != null)
        assert(storedEntity?.name == newStore.nameIn)
        assert(storedStore?.streetAddress == newStore.streetAddress)
    }
}