package pro.informatiq.shopscape.database.repositories


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.Database.Entities.StoreEntity
import java.util.*

@Repository
interface StoreRepository : JpaRepository<StoreEntity, UUID> {
}