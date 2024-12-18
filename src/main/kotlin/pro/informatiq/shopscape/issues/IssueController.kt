package pro.informatiq.shopscape.issues

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
@RestController
class IssueController(val issueService: IssueService) {
    @GetMapping("/api/issues")
    fun getShopsWithIssues(): ResponseEntity<Map<String, List<IssueService.StoreIssueSummary>>> {
        val storeSummaries = issueService.getStoreIssueSummaries()
        val responseBody = mapOf("shops" to storeSummaries)
        return ResponseEntity.ok(responseBody)
    }
}