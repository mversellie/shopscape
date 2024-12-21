package pro.informatiq.shopscape.data

import java.util.*

abstract class MainEntityResponse{
    abstract val id: UUID
    abstract val name: String
    abstract var requests: List<Request>?
    abstract var issues: List<Issue>?

    fun inflateRequestsAndIssues(){
        requests = requests ?: emptyList()
        issues = issues ?: emptyList()
    }
}