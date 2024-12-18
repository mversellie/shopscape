package pro.informatiq.shopscape.issues

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pro.informatiq.shopscape.database.repositories.IssueRepository
import pro.informatiq.shopscape.database.repositories.StoreWithIssueCountsProjection
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
@ExtendWith(MockKExtension::class)
class IssueServiceTest {
 private val issueRepository: IssueRepository = mockk()
 private val issueService = IssueService(issueRepository)
 @Test
 fun `getStoreIssueSummaries should return a list of StoreIssueSummary`() {
  val mockProjection = mockk<StoreWithIssueCountsProjection>()
  val storeId = UUID.randomUUID()
  every { mockProjection.getEntityId() } returns storeId
  every { mockProjection.getEntityName() } returns "Store Name"
  every { mockProjection.getStreetAddress() } returns "123 Main St"
  every { mockProjection.getCity() } returns "Anytown"
  every { mockProjection.getState() } returns "CA"
  every { mockProjection.getZipCode() } returns "91234"
  every { mockProjection.getPhoneNumber() } returns "555-123-4567"
  every { mockProjection.getIssueCount() } returns 5L
  every { mockProjection.getEquipmentIssueCount() } returns 2L
  val mockProjectionList = listOf(mockProjection)
  every { issueRepository.findAllStoresWithIssueCounts() } returns mockProjectionList
  val storeSummaries = issueService.getStoreIssueSummaries()
  assertNotNull(storeSummaries)
  assertEquals(1, storeSummaries.size)
  val summary = storeSummaries.first()
  assertEquals(storeId, summary.id)
  assertEquals("Store Name", summary.name)
  assertEquals("123 Main St", summary.streetAddress)
  assertEquals("Anytown", summary.city)
  assertEquals("CA", summary.state)
  assertEquals("91234", summary.zipCode)
  assertEquals("555-123-4567", summary.phoneNumber)
  assertEquals(5L, summary.issueCount)
  assertEquals(2L, summary.equipmentIssueCount)
 }
}