package pro.informatiq.shopscape.issues

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
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IssueControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var storeRepository: StoreRepository

    private val objectMapper = jacksonObjectMapper()

    @Autowired
    lateinit var mainEntityRepository: MainEntityRepository

    @Autowired
    lateinit var equipmentRepository: EquipmentRepository

    @Autowired
    lateinit var equipmentRelationshipRepository: EquipmentRelationshipRepository

    @Autowired
    lateinit var issuesRepository: IssueRepository

    private lateinit var equipmentId: UUID
    private lateinit var storeID: UUID
    private lateinit var entityId: UUID
    private var before:Long = 0


    @BeforeEach
    fun setUp() {
            before = issuesRepository.count()
            equipmentId = UUID.randomUUID()
            storeID = UUID.randomUUID()
            entityId = UUID.randomUUID()
            val testMainEntity = MainEntity(storeID, "Test Store", 1)
            val testMainEntity2 = MainEntity(equipmentId, "Test Equipment", 2)

            mainEntityRepository.saveAll(listOf(testMainEntity,testMainEntity2))

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

            val issueEntity1 = IssueEntity(
                id = UUID.randomUUID(),
                entityId = storeID,
                name = "Request for new equipment",
                description = "Store needs a new coffee machine."
            )

            val issueEntity2 = IssueEntity(
                id = UUID.randomUUID(),
                entityId = equipmentId,
                name = "Repair request",
                description = "Refrigerator is not cooling properly."
            )

        issuesRepository.saveAll(listOf(issueEntity1,issueEntity2))
    }

    /*
    @Test
    fun `getShopsWithIssues should return shops with issues`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/issues")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
        // TODO: Add assertions to verify the returned data
    }
     */

    @Test
    fun `getTotalIssues should return the correct count`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/issues/total-issues")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mapOf("count" to (2L + before)))))
    }
}