package pro.informatiq.shopscape.entities

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/entities")
class MainEntityController(val mainEntityService: MainEntityService) {
    @PostMapping
    fun createEntity(@RequestBody request: CreateEntityRequest): ResponseEntity<Unit> {
        val entityId = UUID.randomUUID()
        mainEntityService.createMainEntity(request.name, request.entityTypeId,entityId)
        return ResponseEntity(HttpStatus.CREATED)
    }
    data class CreateEntityRequest(val name: String, val entityTypeId: Int)
}