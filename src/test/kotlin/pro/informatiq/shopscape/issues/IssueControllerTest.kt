package pro.informatiq.shopscape.issues

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
@ExtendWith(MockKExtension::class)
class IssueControllerTest {
 private val issueService: IssueService = mockk()
 private val issueController = IssueController(issueService)
 @Test
 fun `getShopsWithIssues should return a 200 with a list of StoreIssueSummary`() {
  val mockSummaries = listOf(
   IssueService.StoreIssueSummary(
    id = UUID.randomUUID(),
    name = "Store A",
    streetAddress = "123 Main St",
    city = "Anytown",
    state = "CA",
    zipCode = "91234",
    phoneNumber = "555-123-4567",
    issueCount = 5,
    equipmentIssueCount = 2
   )
  )
  every { issueService.getStoreIssueSummaries() } returns mockSummaries
  val response = issueController.getShopsWithIssues()
  assertEquals(HttpStatus.OK, response.statusCode)
  val body = response.body
  assertNotNull(body)
  assertEquals(1, (body as Map<String, List<IssueService.StoreIssueSummary>>)["shops"]?.size)
 }
}