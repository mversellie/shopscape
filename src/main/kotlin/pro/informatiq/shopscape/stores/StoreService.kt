package pro.informatiq.shopscape.stores

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pro.informatiq.shopscape.database.entities.StoreEntity
import pro.informatiq.shopscape.database.repositories.StoreRepository
import pro.informatiq.shopscape.entities.EntityTypeEnum
import pro.informatiq.shopscape.entities.MainEntityService
import java.util.*
@Service
class StoreService(val storeRepository: StoreRepository, val entityService:MainEntityService) {

    @Transactional
    fun createStore(name: String, streetAddress: String, city: String, state: String, zipCode: String, phoneNumber: String,entityId:UUID) {
         entityService.createMainEntity(name, EntityTypeEnum.STORE.typeCode,entityId)
        val newStore = StoreEntity(
            entityId = entityId,
            streetAddress = streetAddress,
            city = city,
            state = state,
            zipCode = zipCode,
            phoneNumber = phoneNumber
        )
        storeRepository.save(newStore)
    }
}