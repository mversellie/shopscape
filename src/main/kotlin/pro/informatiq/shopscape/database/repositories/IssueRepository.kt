package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.data.Issue
import pro.informatiq.shopscape.database.entities.IssueEntity
import java.util.*

@Repository
interface IssueRepository : JpaRepository<IssueEntity, UUID> {

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Issue(i.id, i.name, i.description, i.entityId, it.name) " +
                "FROM IssueEntity i,IssueTypeEntity it WHERE i.status = it.id"
    )
    fun findAllIssuesWithTypes(): List<Issue>

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Issue(i.id, i.name, i.description, i.entityId, it.name) " +
                "FROM IssueEntity i,IssueTypeEntity it WHERE i.status = it.id AND i.id in :entities"
    )
    fun findAllIssuesWithTypesForEntitiesInList(entities: List<UUID>): List<Issue>

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Issue(i.id, i.name, i.description, i.entityId, it.name) " +
                "FROM IssueEntity i,IssueTypeEntity it WHERE i.status = it.id AND i.id = :id"
    )
    fun findAllIssuesWithTypesForEntity(id: UUID): List<Issue>

}