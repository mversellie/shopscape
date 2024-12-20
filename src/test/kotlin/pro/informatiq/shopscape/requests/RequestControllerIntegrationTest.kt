package pro.informatiq.shopscape.requests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pro.informatiq.shopscape.database.entities.*
import pro.informatiq.shopscape.database.repositories.*
import pro.informatiq.shopscape.equipment.EquipmentService
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RequestControllerIntegrationTest {

    @Autowired
    private lateinit var equipmentService: EquipmentService

    @Autowired
    private lateinit var storeRepository: StoreRepository
    private val objectMapper = jacksonObjectMapper()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mainEntityRepository: MainEntityRepository

    @Autowired
    lateinit var equipmentRepository: EquipmentRepository

    @Autowired
    lateinit var equipmentRelationshipRepository: EquipmentRelationshipRepository

    @Autowired
    lateinit var requestsRepository: RequestsRepository

    private lateinit var equipmentId: UUID
    private lateinit var storeID: UUID
    private lateinit var entityId: UUID

    @BeforeEach
    fun setUp() {
        equipmentId = UUID.randomUUID()
        storeID = UUID.randomUUID()
        entityId = UUID.randomUUID()
        val testMainEntity = MainEntity(storeID, "Test Store", 1)
        val testMainEntity2 = MainEntity(equipmentId, "Test Equipment", 2)
        mainEntityRepository.saveAll(listOf(testMainEntity2, testMainEntity))

        val testEquipment = EquipmentEntity(
            equipmentId,
            serialNumber = "SN",
            description = "DESC",
            modelNumber = "MN"
        )
        equipmentRepository.save(testEquipment)

        val storeEntity = StoreEntity(
            entityId = storeID,
            streetAddress = "address",
            city = "Anytown",
            state = "CA",
            zipCode = "91234",
            phoneNumber = "555-123-4567"
        )
        storeRepository.save(storeEntity)


        val equipmentRelationshipEntity =
            EquipmentRelationshipEntity(UUID.randomUUID(),equipmentId,storeID)
        equipmentRelationshipRepository.save(equipmentRelationshipEntity)

        val requestEntity1 = RequestEntity(
            id = UUID.randomUUID(),
            entityId = storeID, // Assuming this is the MainEntity ID
            name = "Request for new equipment",
            description = "Store needs a new coffee machine."
        )

        val requestEntity2 = RequestEntity(
            id = UUID.randomUUID(),
            entityId = entityId, // Assuming this is a different MainEntity ID
            name = "Repair request",
            description = "Refrigerator is not cooling properly."
        )

        requestsRepository.saveAll(listOf(requestEntity1,requestEntity2))

    }

    /*
    @Test
    @Ignore
    fun `getShopsWithRequests should return shops with requests`() {
        val storeIssueSummary = RequestService.StoreRequestSummary(
            id = storeID,
            name = "Store A",
            streetAddress = "123 Main St",
            city = "Anytown",
            state = "CA",
            zipCode = "91234",
            phoneNumber = "555-123-4567",
            requestCount =  1,
            equipmentRequestCount = 1
        )

        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mapOf("shops" to listOf(storeIssueSummary)))))
    }

     */
    @Test
    fun `getTotalRequests should return the correct count`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/total-requests")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mapOf("count" to 2L))))
    }
}