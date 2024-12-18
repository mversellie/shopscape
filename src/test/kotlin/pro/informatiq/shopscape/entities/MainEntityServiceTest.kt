package pro.informatiq.shopscape.entities
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import pro.informatiq.shopscape.database.repositories.MainEntityRepository
import java.util.*
@ExtendWith(MockKExtension::class)
class MainEntityServiceTest {
 private val mainEntityRepository: MainEntityRepository = mockk()
 private val mainEntityService = MainEntityService(mainEntityRepository)
 @Test
 fun `createMainEntity should save a new entity`() {
  val mockEntityId = UUID.randomUUID()
  val name = "Test Entity"
  val entityTypeId = 1
  every {mainEntityRepository.save( any())} answers { mockk() }
  mainEntityService.createMainEntity(name,entityTypeId,mockEntityId)
  verify(exactly = 1) { mainEntityRepository.save(any()) }
 }
}