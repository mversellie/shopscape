package pro.informatiq.shopscape.stores

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pro.informatiq.shopscape.data.Store
import pro.informatiq.shopscape.database.entities.StoreEntity
import pro.informatiq.shopscape.database.repositories.StoreRepository
import pro.informatiq.shopscape.entities.EntityTypeEnum
import pro.informatiq.shopscape.entities.MainEntityService
import pro.informatiq.shopscape.equipment.EquipmentService
import pro.informatiq.shopscape.issues.IssueService
import pro.informatiq.shopscape.requests.RequestService
import java.util.*
@Service
class StoreService(val storeRepository: StoreRepository, val entityService:MainEntityService,
                   private val equipmentService: EquipmentService, private val issueService: IssueService,
                   private val requestService: RequestService
) {

    @Transactional
    fun createStore(
        name: String,
        streetAddress: String,
        city: String,
        state: String,
        zipCode: String,
        phoneNumber: String,
        entityId: UUID
    ) {
        entityService.createMainEntity(name, EntityTypeEnum.STORE.typeCode, entityId)
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

    fun getAllStoresWithEquipment(): List<Store> {
        val stores = storeRepository.findAllAsPojo()
        val storeIds = stores.map { it.id }
        val requestsGrouped = requestService.getAllRequestsForEntities(storeIds).groupBy { it.entityId }
        val issuesGrouped = issueService.getAllIssuesWithTypesForEntities(storeIds).groupBy { it.entityId }
        for (store in stores) {
            store.equipment = equipmentService.getEquipmentForStore(store.id)
            store.inflateRequestsAndIssues()
            store.requests=requestsGrouped[store.id].orEmpty()
            store.issues=issuesGrouped[store.id].orEmpty()
        }
        return stores
    }

    fun getStoreById(storeId:UUID): Store? {
        val store = storeRepository.findByStoreId(storeId)
        if (store != null) {
            store.equipment = equipmentService.getEquipmentForStore(store.id)
            store.inflateRequestsAndIssues()
            store.requests= requestService.getAllRequestsForID(store.id)
            store.issues=issueService.getIssueWithTypesForEntity(store.id)
            return store
        }
        return null
    }


}