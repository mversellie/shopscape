package pro.informatiq.shopscape.entities

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertNotNull
import pro.informatiq.shopscape.database.entities.MainEntity

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class MainEntityControllerIntegrationTest {
 @Autowired
 private lateinit var mockMvc: MockMvc
 @PersistenceContext
 private lateinit var entityManager: EntityManager
 @Test
 fun `createEntity should return CREATED status and persist entity`() {
  val requestBody = """
            {
                "name": "Test Entity",
                "entityTypeId": 1
            }
        """.trimIndent()
  mockMvc.perform(MockMvcRequestBuilders.post("/api/entities")
   .contentType(MediaType.APPLICATION_JSON)
   .content(requestBody))
   .andExpect(MockMvcResultMatchers.status().isCreated)
  val createdEntity = entityManager.createQuery(
   "SELECT e FROM MainEntity e WHERE e.name = :name", MainEntity::class.java
  )
   .setParameter("name", "Test Entity")
   .singleResult
  assertNotNull(createdEntity)
  assertEquals("Test Entity", createdEntity.name)
  assertEquals(1, createdEntity.entityType)
 }
}
