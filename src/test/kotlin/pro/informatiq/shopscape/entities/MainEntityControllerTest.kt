package pro.informatiq.shopscape.entities

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.junit5.MockKExtension
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
@ExtendWith(MockKExtension::class)
class MainEntityControllerTest {
 private val mainEntityService: MainEntityService = mockk()
 private val mainEntityController = MainEntityController(mainEntityService)
 @Test
 fun `createEntity should return CREATED status`() {
  val request = MainEntityController.CreateEntityRequest("Test Entity", 1)
  every { mainEntityService.createMainEntity(request.name, request.entityTypeId,any()) } returns mockk()
  val response = mainEntityController.createEntity(request)
  assertEquals(HttpStatus.CREATED, response.statusCode)
  assertEquals(null, response.body)
 }
}