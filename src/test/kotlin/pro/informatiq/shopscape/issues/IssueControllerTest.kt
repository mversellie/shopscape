package pro.informatiq.shopscape.issues

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pro.informatiq.shopscape.data.Issue
import java.util.*
import kotlin.test.assertEquals
@ExtendWith(MockKExtension::class)
class IssueControllerTest {
 private val issueService: IssueService = mockk()
 private val issueController = IssueController(issueService)
 /*
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
*/
 @Test
 fun `getTotalIssues should return Ok with the correct count`() {
  // Given
  val expectedCount = 15L
  every { issueService.getTotalIssueCount() } returns expectedCount

  // When
  val response = issueController.getTotalIssues()

  // Then
  assertEquals(HttpStatus.OK, response.statusCode)
  assertEquals(expectedCount, response.body?.get("count"))
 }

 @Test
 fun `getAllIssues should return a ResponseEntity with a list of issues`() {
  // Given
  val issues = listOf(
   Issue(UUID.randomUUID(), "Issue 1", "Description 1", UUID.randomUUID(), "Bug"),
   Issue(UUID.randomUUID(), "Issue 2", "Description 2", UUID.randomUUID(), "Feature Request")
  )
  every { issueService.getAllIssuesWithTypes() } returns issues

  // When
  val responseEntity: ResponseEntity<Map<String, List<Issue>>> = issueController.getAllIssues()

  // Then
  assert(responseEntity.statusCode.is2xxSuccessful)
  assertEquals(issues, responseEntity.body?.get("issues"))
 }

}