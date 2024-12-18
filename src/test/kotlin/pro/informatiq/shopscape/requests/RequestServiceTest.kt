package pro.informatiq.shopscape.requests

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pro.informatiq.shopscape.database.repositories.RequestsRepository
import pro.informatiq.shopscape.database.repositories.StoreWithRequestCountsProjection
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
@ExtendWith(MockKExtension::class)
class RequestServiceTest {
 private val requestRepository: RequestsRepository = mockk()
 private val requestService = RequestService(requestRepository)
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
}