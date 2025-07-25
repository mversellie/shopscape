package pro.informatiq.shopscape.requests

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.junit5.MockKExtension
import org.springframework.http.HttpStatus
import pro.informatiq.shopscape.data.Request
import java.util.*
import kotlin.test.assertEquals
@ExtendWith(MockKExtension::class)
class RequestControllerTest {
 private val requestService: RequestService = mockk()
 private val requestController = RequestController(requestService)
 /*
 @Test
 fun `getShopsWithRequests should return a 200 with a list of StoreRequestSummary`() {
  val mockSummaries = listOf(
   RequestService.StoreRequestSummary(
    id = UUID.randomUUID(),
    name = "Store A",
    streetAddress = "123 Main St",
    city = "Anytown",
    state = "CA",
    zipCode = "91234",
    phoneNumber = "555-123-4567",
    requestCount = 5,
    equipmentRequestCount = 2
   )
  )
  every { requestService.getStoreRequestSummaries() } returns mockSummaries
  val response = requestController.getShopsWithRequests()
  assertEquals(HttpStatus.OK, response.statusCode)
  val body = response.body
  assertNotNull(body)
  val shops = (body)["shops"]
  assertNotNull(shops)
  assertEquals(1, shops?.size)
  val firstShop = shops.first()
  assertNotNull(firstShop)
  assertEquals("Store A", firstShop.name)
  assertEquals("123 Main St", firstShop.streetAddress)
  assertEquals("Anytown", firstShop.city)
  assertEquals("CA", firstShop.state)
  assertEquals("91234", firstShop.zipCode)
  assertEquals("555-123-4567", firstShop.phoneNumber)
  assertEquals(5, firstShop.requestCount)
  assertEquals(2, firstShop.equipmentRequestCount)
 }
*/
 @Test
 fun `getTotalRequests should return Ok with the correct count`() {
  // Given
  val expectedCount = 20L
  every { requestService.getTotalRequestCount() } returns expectedCount

  // When
  val response = requestController.getTotalRequests()

  // Then
  assertEquals(HttpStatus.OK, response.statusCode)
  assertEquals(expectedCount, response.body?.get("count"))
 }

 @Test
 fun `getAllRequests should return 200 OK with requests data`() {
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

  every { requestService.getAllRequestsWithStatus() } answers {mockRequests}

  val response = requestController.getAllRequests()

  assert(response.statusCode == HttpStatus.OK)
  val body = response.body
  assert(body != null) // Ensure body is not null

  assert(body!!["requests"] is List<*>)
  val requests = body["requests"] as List<Request>
  assert(requests.size == 2)
  assert(requests[0].status == "Pending")
  assert(requests[1].status == "Completed")
 }


}