package pro.informatiq.shopscape.stores
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pro.informatiq.shopscape.data.Store
import java.util.*
import kotlin.collections.Map

@RestController
class StoreController(val storeService: StoreService) {
    @PostMapping("/stores")
    fun createStore(@RequestBody store: Store): ResponseEntity<Store> {
        val entityId = UUID.randomUUID()
        storeService.createStore(
            name = store.name,
            streetAddress = store.streetAddress,
            city = store.city,
            state = store.state,
            zipCode = store.zipCode,
            phoneNumber = store.phoneNumber,
            entityId = entityId
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(store.copy(id = entityId))
    }

    @GetMapping("/stores")
    fun getAllStores(): ResponseEntity<Map<String,List<Store>>> {
        val stores = storeService.getAllStoresWithEquipment()
        return ResponseEntity.ok(mapOf("stores" to stores))
    }
}