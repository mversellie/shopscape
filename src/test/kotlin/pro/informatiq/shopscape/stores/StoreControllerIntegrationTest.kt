package pro.informatiq.shopscape.controller

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
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.database.entities.EquipmentEntity
import pro.informatiq.shopscape.database.entities.EquipmentRelationshipEntity
import pro.informatiq.shopscape.database.entities.MainEntity
import pro.informatiq.shopscape.database.entities.StoreEntity
import pro.informatiq.shopscape.database.repositories.EquipmentRelationshipRepository
import pro.informatiq.shopscape.database.repositories.EquipmentRepository
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
    @Autowired
    private lateinit var equipmentRelationshipRepository: EquipmentRelationshipRepository


    @Autowired
    private lateinit var equipmentRepository: EquipmentRepository

    @Test
    fun `should create a new store - integration test`() {
        val newStore = Store(
            id = UUID.randomUUID(),
            streetAddress = "123 Main St",
            city = "Anytown",
            state = "CA",
            zipCode = "91234",
            phoneNumber = "555-123-4567",
            name = "My Store"
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
        assert(storedEntity?.name == newStore.name)
        assert(storedStore?.streetAddress == newStore.streetAddress)
    }

        @Test
        fun `should get all stores with equipment`() {
            // Create some sample stores and equipment


            val mainEntity1 = MainEntity(
                id = UUID.randomUUID(),
                name = "Store 1",
                entityType = 1
            )

            val mainEntity2 = MainEntity(
                id = UUID.randomUUID(),
                name = "Store 2",
                entityType = 1
            )

            val mainEntity3 = MainEntity(
                id = UUID.randomUUID(),
                name = "Hammer",
                entityType = 2
            )

            val mainEntity4 = MainEntity(
                id = UUID.randomUUID(),
                name = "Saw",
                entityType = 2
            )

            val mainEntity5 = MainEntity(
                id = UUID.randomUUID(),
                name = "Drill",
                entityType = 2
            )

            val store1 = Store(
                id = mainEntity1.id,
                streetAddress = "123 Main St",
                city = "Anytown",
                state = "CA",
                zipCode = "91234",
                phoneNumber = "555-123-4567",
                name = "Store 1"
            )
            val store2 = Store(
                id = mainEntity2.id,
                streetAddress = "456 Oak Ave",
                city = "Springfield",
                state = "IL",
                zipCode = "62701",
                phoneNumber = "123-456-7890",
                name = "Store 2"
            )

            val storeEntity1 = StoreEntity(
                entityId = mainEntity1.id,
                streetAddress = "123 Main St",
                city = "Anytown",
                state = "CA",
                zipCode = "91234",
                phoneNumber = "555-123-4567"
            )
            val storeEntity2 = StoreEntity(
                entityId = mainEntity2.id,
                streetAddress = "456 Oak Ave",
                city = "Springfield",
                state = "IL",
                zipCode = "62701",
                phoneNumber = "123-456-7890"
            )

            val equipment1 = Equipment(
                serialNumber = "EQ12345",
                description = "Hammer",
                modelNumber = "HM-100",
                id = mainEntity3.id,
                name = "Hammer"
            )
            val equipment2 = Equipment(
                serialNumber = "EQ67890",
                description = "Saw",
                modelNumber = "SW-200",
                id = mainEntity4.id,
                name = "Saw"
            )
            val equipment3 = Equipment(
                serialNumber = "EQ11111",
                description = "Drill",
                modelNumber = "DR-300",
                id = mainEntity5.id,
                name = "Drill"
            )

            val equipmentEntity1 = EquipmentEntity(
                entityId = mainEntity3.id,
                serialNumber = "EQ12345",
                description = "Hammer",
                modelNumber = "HM-100"
            )
            val equipmentEntity2 = EquipmentEntity(
                entityId = mainEntity4.id,
                serialNumber = "EQ67890",
                description = "Saw",
                modelNumber = "SW-200"
            )
            val equipmentEntity3 = EquipmentEntity(
                entityId = mainEntity5.id,
                serialNumber = "EQ11111",
                description = "Drill",
                modelNumber = "DR-300"
            )
            equipment3.inflateRequestsAndIssues()
            equipment2.inflateRequestsAndIssues()
            equipment1.inflateRequestsAndIssues()
            store1.inflateRequestsAndIssues()
            store2.inflateRequestsAndIssues()

            store1.equipment = listOf(equipment1, equipment2).toMutableList()
            store2.equipment = listOf(equipment3).toMutableList()
            mainEntityRepository.saveAll(listOf(mainEntity1,mainEntity2, mainEntity3,mainEntity4,mainEntity5))
            storeRepository.saveAll(listOf(storeEntity1,storeEntity2))
            equipmentRepository.saveAll(listOf(equipmentEntity1, equipmentEntity2, equipmentEntity3))

            val relationships = listOf(
                EquipmentRelationshipEntity(equipment = equipment1.id, store = store1.id),
                EquipmentRelationshipEntity(equipment = equipment2.id, store = store1.id),
                EquipmentRelationshipEntity(equipment = equipment3.id, store = store2.id)
            )

            equipmentRelationshipRepository.saveAll(relationships)

            val expected_map = mapOf(
                "stores" to listOf(store1, store2)
            )

            print(objectMapper.writeValueAsString(expected_map))


            mockMvc.perform(MockMvcRequestBuilders.get("/stores"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected_map)))
        }




}