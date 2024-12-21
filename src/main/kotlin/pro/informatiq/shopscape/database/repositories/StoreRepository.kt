package pro.informatiq.shopscape.database.repositories


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.database.entities.StoreEntity
import java.util.*

@Repository
interface StoreRepository : JpaRepository<StoreEntity, UUID> {
    fun findByStreetAddress(streetAddress: String): StoreEntity?

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Store(me.id,me.name,e.streetAddress,e.city,e.state,e.zipCode,e.phoneNumber,null,null,null) FROM StoreEntity e " +
                "JOIN MainEntity me ON e.entityId = me.id "
    )
    fun findAllAsPojo():MutableList<Store>
}