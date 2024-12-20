package pro.informatiq.shopscape.requests

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/requests")
class RequestController(val requestService: RequestService) {
    //@GetMapping("/api/requests")
    fun getShopsWithRequests(): ResponseEntity<Map<String, List<RequestService.StoreRequestSummary>>> {
        val storeSummaries = requestService.getStoreRequestSummaries()
        val responseBody = mapOf("shops" to storeSummaries)
        return ResponseEntity.ok(responseBody)
    }

    @GetMapping("/total-requests")
    fun getTotalRequests(): ResponseEntity<Map<String, Long>> {
        val totalRequests = requestService.getTotalRequestCount()
        val responseBody = mapOf("count" to totalRequests)
        return ResponseEntity.ok(responseBody)
    }

}