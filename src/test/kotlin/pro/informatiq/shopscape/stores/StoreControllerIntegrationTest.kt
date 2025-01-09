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
import pro.informatiq.shopscape.database.entities.*
import pro.informatiq.shopscape.database.repositories.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StoreControllerIntegrationTest {
    @Autowired
    private lateinit var requestsRepository: RequestsRepository

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
            MockMvcRequestBuilders.post("/api/stores")
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


            mockMvc.perform(MockMvcRequestBuilders.get("/api/stores"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected_map)))
        }


    @Test
    fun `should get a store by ID`() {
        // 1. Create a store for testing
        val store = Store(
            id = UUID.randomUUID(),
            streetAddress = "123 Main St",
            city = "Anytown",
            state = "CA",
            zipCode = "91234",
            phoneNumber = "555-123-4567",
            name = "My Store"
        )

        val mainEntity = MainEntity(id = store.id, name = store.name, entityType = 1)
        val storeEntity = StoreEntity(entityId = store.id,
            streetAddress = store.streetAddress,
            city = store.city,
            state = store.state,
            zipCode = store.zipCode,
            phoneNumber = store.phoneNumber)
        mainEntityRepository.save(mainEntity)
        storeRepository.save(storeEntity)

        // 2. Make the request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stores/${store.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(store)))
    }

    @Test
    fun `should return 404 when store not found`() {
        val nonExistentId = UUID.randomUUID()
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stores/${nonExistentId}"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `should get requests for a store`() {
        val store = StoreEntity(
            entityId = UUID.randomUUID(),
            streetAddress = "123 Main St",
            city = "Anytown",
            state = "CA",
            zipCode = "91234",
            phoneNumber = "555-123-4567",
        )

        val storeName = "My Store"
        val equipmentName = "Hammer"

        val equipment1 = EquipmentEntity(
            entityId =  UUID.randomUUID(),
            serialNumber = "EQ12345",
            description = "Heavy Hammer",
            modelNumber = "HM-100"
        )

        val request1 = RequestEntity(id = UUID.randomUUID(), name = "Request 1", description = "Desc 1", entityId = equipment1.entityId)
        val request2 = RequestEntity(id = UUID.randomUUID(), name = "Request 2", description = "Desc 2", entityId = store.entityId)

        val mainEntity = MainEntity(id = store.entityId, name = storeName, entityType = 1)
        val mainEntity2 = MainEntity(id = equipment1.entityId, name = equipmentName, entityType = 2)

        // Create relationship entities
        val storeEquipment1 = EquipmentRelationshipEntity(id = UUID.randomUUID(), store = store.entityId, equipment = equipment1.entityId)

        mainEntityRepository.saveAll(listOf( mainEntity,mainEntity2))
        storeRepository.save(store)
        equipmentRepository.save(equipment1)
        equipmentRelationshipRepository.save(storeEquipment1)
        requestsRepository.saveAll(listOf(request1, request2))

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stores/${store.entityId}/requests"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.singletonMap("requests",listOf(request1, request2)))))
    }






}