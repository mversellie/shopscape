package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.database.entities.MainEntity
import java.util.*
@Repository
interface MainEntityRepository : JpaRepository<MainEntity, UUID> {
    @Modifying
    @Query("INSERT INTO entities (id, name, entity_type_id) VALUES (:id, :name, :entityTypeId)", nativeQuery = true)
    fun makeEntity(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("entityTypeId") entityTypeId: Int
    )
}