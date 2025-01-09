package pro.informatiq.shopscape.stores
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pro.informatiq.shopscape.data.Store
import java.util.*
import kotlin.collections.Map

@RestController
@RequestMapping("/api/stores")
class StoreController(
    val storeService: StoreService,
) {
    @PostMapping
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

    @GetMapping
    fun getAllStores(): ResponseEntity<Map<String,List<Store>>> {
        val stores = storeService.getAllStoresWithEquipment()
        return ResponseEntity.ok(mapOf("stores" to stores))
    }

    @GetMapping("/{id}")
    fun getStoreById(@PathVariable id: UUID): ResponseEntity<Store?> {
        val store = storeService.getStoreById(id)
        store?.let { return ResponseEntity.ok(store)}
        return ResponseEntity.notFound().build()
    }

}