package pro.informatiq.shopscape.issues

import org.springframework.stereotype.Service
import pro.informatiq.shopscape.database.repositories.IssueRepository
import java.util.*
@Service
class IssueService(val issueRepository: IssueRepository) {
    data class StoreIssueSummary(
        val id: UUID,
        val name: String,
        val streetAddress: String,
        val city: String,
        val state: String,
        val zipCode: String,
        val phoneNumber: String,
        val issueCount: Long,
        val equipmentIssueCount: Long
    )
    fun getStoreIssueSummaries(): List<StoreIssueSummary> {
        return issueRepository.findAllStoresWithIssueCounts().map { projection ->
            StoreIssueSummary(
                id = projection.getEntityId(),
                name = projection.getEntityName(),
                streetAddress = projection.getStreetAddress(),
                city = projection.getCity(),
                state = projection.getState(),
                zipCode = projection.getZipCode(),
                phoneNumber = projection.getPhoneNumber(),
                issueCount = projection.getIssueCount(),
                equipmentIssueCount = projection.getEquipmentIssueCount()
            )
        }
    }
    fun getTotalIssueCount(): Long {
        return issueRepository.count()
    }
}