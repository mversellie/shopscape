package pro.informatiq.shopscape.requests

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
@RestController
class RequestController(val requestService: RequestService) {
    @GetMapping("/api/requests")
    fun getShopsWithRequests(): ResponseEntity<Map<String, List<RequestService.StoreRequestSummary>>> {
        val storeSummaries = requestService.getStoreRequestSummaries()
        val responseBody = mapOf("shops" to storeSummaries)
        return ResponseEntity.ok(responseBody)
    }
}