package pro.informatiq.shopscape.issues

import org.springframework.stereotype.Service
import pro.informatiq.shopscape.data.Issue
import pro.informatiq.shopscape.database.repositories.IssueRepository
import java.util.*
@Service
class IssueService(val issueRepository: IssueRepository) {

    fun getTotalIssueCount(): Long {
        return issueRepository.count()
    }


    fun getAllIssuesWithTypes(): List<Issue> {
        return issueRepository.findAllIssuesWithTypes()
    }

    fun getAllIssuesWithTypesForEntities(entities: List<UUID>): List<Issue> {
        return issueRepository.findAllIssuesWithTypesForEntitiesInList(entities)
    }

    fun getIssueWithTypesForEntity(id: UUID): List<Issue> {
        return issueRepository.findAllIssuesWithTypesForEntity(id)
    }



}