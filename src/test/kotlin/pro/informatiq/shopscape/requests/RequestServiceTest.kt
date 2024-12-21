package pro.informatiq.shopscape.requests

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pro.informatiq.shopscape.data.Request
import pro.informatiq.shopscape.database.repositories.RequestsRepository
import java.util.*
import kotlin.test.assertEquals
@ExtendWith(MockKExtension::class)
class RequestServiceTest {
 private val requestRepository: RequestsRepository = mockk()
 private val requestService = RequestService(requestRepository)
 /*
 @Test
 fun `getStoreRequestSummaries should return a list of StoreRequestSummary`() {
  val key = UUID.randomUUID()
  val mockProjection = mockk<StoreWithRequestCountsProjection>()
  every { mockProjection.getEntityId() } returns key
  every { mockProjection.getEntityName() } returns "Store Name"
  every { mockProjection.getStreetAddress() } returns "123 Main St"
  every { mockProjection.getCity() } returns "Anytown"
  every { mockProjection.getState() } returns "CA"
  every { mockProjection.getZipCode() } returns "91234"
  every { mockProjection.getPhoneNumber() } returns "555-123-4567"
  every { mockProjection.getRequestCount() } returns 5L
  every { mockProjection.getEquipmentRequestCount() } returns 2L
  val mockProjectionList = listOf(mockProjection)
  every { requestRepository.findAllStoresWithRequestCounts() } returns mockProjectionList
  val storeSummaries = requestService.getStoreRequestSummaries()
  assertNotNull(storeSummaries)
  assertEquals(1, storeSummaries.size)
  val summary = storeSummaries.first()
  assertEquals(key, summary.id)
  assertEquals("Store Name", summary.name)
  assertEquals("123 Main St", summary.streetAddress)
  assertEquals("Anytown", summary.city)
  assertEquals("CA", summary.state)
  assertEquals("91234", summary.zipCode)
  assertEquals("555-123-4567", summary.phoneNumber)
  assertEquals(5L, summary.requestCount)
  assertEquals(2L, summary.equipmentRequestCount)
 }
*/
 @Test
 fun `getTotalRequestCount should return the correct count`() {
  // Given
  val expectedCount = 5L
  every { requestRepository.count() } returns expectedCount

  // When
  val actualCount = requestService.getTotalRequestCount()

  // Then
  assertEquals(expectedCount, actualCount)
 }

 @Test
 fun `getAllRequestsWithStatus should return requests from repository`() {
  val mockRequests = listOf(
   Request(
    id = UUID.randomUUID(),
    entityId = UUID.randomUUID(),
    name = "Request 1",
    status = "Pending",
    description = "Some description"
   ),
   Request(
    id = UUID.randomUUID(),
    entityId = UUID.randomUUID(),
    name = "Request 2",
    status = "Completed",
    description = "Another description"
   )
  )

  every{requestRepository.findAllRequestsWithStatus()} answers {mockRequests}

  val actualRequests = requestService.getAllRequestsWithStatus()

  assert(actualRequests.size == 2)
  assert(actualRequests[0].status == "Pending")
  assert(actualRequests[1].status == "Completed")
 }

  @Test
  fun `getAllRequestsForEntities should return requests for given entities`() {
   val entityIds = listOf(UUID.randomUUID(), UUID.randomUUID())
   val mockRequests = listOf(
    Request(
     id = UUID.randomUUID(),
     entityId = entityIds[0],
     name = "Request 1",
     status = "Pending",
     description = "Some description"
    ),
    Request(
     id = UUID.randomUUID(),
     entityId = entityIds[1],
     name = "Request 2",
     status = "Completed",
     description = "Another description"
    )
   )

   every { requestRepository.findAllRequestsForEntities(entityIds) } returns mockRequests

   val actualRequests = requestService.getAllRequestsForEntities(entityIds)

   assertEquals(2, actualRequests.size)
   assert(actualRequests.any { it.entityId == entityIds[0] })
   assert(actualRequests.any { it.entityId == entityIds[1] })
  }

  @Test
  fun `getAllRequestsForID should return requests for given ID`() {
   val entityId = UUID.randomUUID()
   val mockRequests = listOf(
    Request(
     id = UUID.randomUUID(),
     entityId = entityId,
     name = "Request 1",
     status = "Pending",
     description = "Some description"
    ),
    Request(
     id = UUID.randomUUID(),
     entityId = entityId,
     name = "Request 2",
     status = "Completed",
     description = "Another description"
    )
   )

   every { requestRepository.findAllRequestsForID(entityId) } returns mockRequests

   val actualRequests = requestService.getAllRequestsForID(entityId)

   assertEquals(2, actualRequests.size)
   actualRequests.forEach { assertEquals(entityId, it.entityId) }
  }

}