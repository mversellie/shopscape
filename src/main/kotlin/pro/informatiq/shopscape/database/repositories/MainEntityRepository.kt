package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.database.entities.MainEntity
import java.util.*
@Repository
interface MainEntityRepository : JpaRepository<MainEntity, UUID> {
    fun findByName(name: String): MainEntity?
}