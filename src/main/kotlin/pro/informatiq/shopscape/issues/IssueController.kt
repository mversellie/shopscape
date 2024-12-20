package pro.informatiq.shopscape.issues

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/issues")
class IssueController(val issueService: IssueService) {
    //@GetMapping
    fun getShopsWithIssues(): ResponseEntity<Map<String, List<IssueService.StoreIssueSummary>>> {
        val storeSummaries = issueService.getStoreIssueSummaries()
        val responseBody = mapOf("shops" to storeSummaries)
        return ResponseEntity.ok(responseBody)
    }

    @GetMapping("/total-issues")
    fun getTotalIssues(): ResponseEntity<Map<String, Long>> {
        val totalIssues = issueService.getTotalIssueCount()
        val responseBody = mapOf("count" to totalIssues)
        return ResponseEntity.ok(responseBody)
    }
}