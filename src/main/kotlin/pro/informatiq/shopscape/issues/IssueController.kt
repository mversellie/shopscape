package pro.informatiq.shopscape.issues

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import pro.informatiq.shopscape.data.Issue

@RestController
@RequestMapping
class IssueController(val issueService: IssueService) {
    //@GetMapping
    /*
    @GetMapping("/api/shops/issues")
    fun getShopsWithIssues(): ResponseEntity<Map<String, List<IssueService.StoreIssueSummary>>> {
        val storeSummaries = issueService.getStoreIssueSummaries()
        val responseBody = mapOf("shops" to storeSummaries)
        return ResponseEntity.ok(responseBody)
    }
    */

    @GetMapping("/api/issues/total-issues")
    fun getTotalIssues(): ResponseEntity<Map<String, Long>> {
        val totalIssues = issueService.getTotalIssueCount()
        val responseBody = mapOf("count" to totalIssues)
        return ResponseEntity.ok(responseBody)
    }

    @GetMapping("/api/issues")
    fun getAllIssues(): ResponseEntity<Map<String,List<Issue>>> {
        val issues = issueService.getAllIssuesWithTypes()
        return ResponseEntity.ok(mapOf("issues" to issues))
    }

}