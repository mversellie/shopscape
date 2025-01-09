package pro.informatiq.shopscape.stores

import pro.informatiq.shopscape.equipment.EquipmentService

import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pro.informatiq.shopscape.data.Equipment
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.database.entities.StoreEntity
import pro.informatiq.shopscape.database.repositories.StoreRepository
import pro.informatiq.shopscape.entities.EntityTypeEnum
import pro.informatiq.shopscape.entities.MainEntityService
import pro.informatiq.shopscape.issues.IssueService
import pro.informatiq.shopscape.requests.RequestService
import java.util.*

class StoreServiceTest {

 private val storeRepository = mockk<StoreRepository>()
 private val entityService = mockk<MainEntityService>()
 private val equipmentService = mockk<EquipmentService>()
 private val issueService = mockk<IssueService>()
 private val requestService = mockk<RequestService>()
 private lateinit var storeService: StoreService

 @BeforeEach
 fun setUp() {
  storeService = StoreService(
   storeRepository,
   entityService,
   equipmentService,
   issueService,
   requestService
  )
 }

 @Test
 fun `createStore should call createMainEntity and save store entity`() {
  val entityId = UUID.randomUUID()
  val name = "Test Store"
  val streetAddress = "123 Main St"
  val city = "Anytown"
  val state = "CA"
  val zipCode = "91234"
  val phoneNumber = "555-123-4567"

  coEvery { entityService.createMainEntity(name, EntityTypeEnum.STORE.typeCode, entityId) } just Runs
  val storeEntity = StoreEntity(
   entityId = entityId,
   streetAddress = streetAddress,
   city = city,
   state = state,
   zipCode = zipCode,
   phoneNumber = phoneNumber
  )
  coEvery { storeRepository.save(storeEntity) } returns storeEntity

  storeService.createStore(name, streetAddress, city, state, zipCode, phoneNumber, entityId)

  coVerify { entityService.createMainEntity(name, EntityTypeEnum.STORE.typeCode, entityId) }
  coVerify { storeRepository.save(storeEntity) }
 }

 @Test
 fun `getAllStoresWithEquipment should fetch all stores and their equipment`() {
  val storeEntity1 = Store(id = UUID.randomUUID(), name = "Store 1",  streetAddress = "Address 1", city = "City 1", state = "State 1", zipCode = "12345", phoneNumber = "123-456-7890")
  val storeEntity2 = Store(id = UUID.randomUUID(), name= "Store 2", streetAddress = "Address 2", city = "City 2", state = "State 2", zipCode = "67890", phoneNumber = "987-654-3210")
  val equipment1 = mutableListOf<Equipment>()
  val equipment2 = mutableListOf<Equipment>()

  coEvery { storeRepository.findAllAsPojo() } returns mutableListOf(storeEntity1, storeEntity2)
  coEvery { equipmentService.getEquipmentForStore(storeEntity1.id) } returns equipment1
  coEvery { equipmentService.getEquipmentForStore(storeEntity2.id) } returns equipment2
  coEvery { requestService.getAllRequestsForEntities(any())} returns listOf()
  coEvery { issueService.getAllIssuesWithTypesForEntities(any())} returns listOf()

  val stores = storeService.getAllStoresWithEquipment()

  assertEquals(2, stores.size)
  assertEquals(storeEntity1.id, stores[0].id)
  assertEquals(equipment1, stores[0].equipment)
  assertEquals(storeEntity2.id, stores[1].id)
  assertEquals(equipment2, stores[1].equipment)
 }

 @Test
 fun `getStoreById should return store with equipment`(){
  val storeId = UUID.randomUUID()
  val storeEntity = Store(id = storeId, name = "Test Store",  streetAddress = "Address 1", city = "City 1", state = "State 1", zipCode = "12345", phoneNumber = "123-456-7890")
  val equipment = mutableListOf<Equipment>()

  coEvery { storeRepository.findByStoreId(storeId) } returns storeEntity
  coEvery { equipmentService.getEquipmentForStore(storeId) } returns equipment
  coEvery { requestService.getAllRequestsForID(any()) } returns listOf()
  coEvery { issueService.getIssueWithTypesForEntity(any())} returns listOf()
  val store = storeService.getStoreById(storeId)
  assertEquals(storeEntity.id, store?.id)
  assertEquals(storeEntity.name, store?.name)
  assertEquals(equipment, store?.equipment)
 }




}