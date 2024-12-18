package pro.informatiq.shopscape.requests

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.junit5.MockKExtension
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
@ExtendWith(MockKExtension::class)
class RequestControllerTest {
 private val requestService: RequestService = mockk()
 private val requestController = RequestController(requestService)
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
}