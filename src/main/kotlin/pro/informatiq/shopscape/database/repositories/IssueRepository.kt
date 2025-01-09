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
                "FROM IssueEntity i " +
                "JOIN IssueTypeEntity it ON i.status = it.id"
    )
    fun findAllIssuesWithTypes(): List<Issue>

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Issue(i.id, i.name, i.description, i.entityId, it.name) FROM IssueEntity i " +
                "JOIN MainEntity m ON m.id = i.entityId " +
                "JOIN IssueTypeEntity it ON i.status = it.id WHERE m.id IN :entities"
    )
    fun findAllIssuesWithTypesForEntitiesInList(entities: List<UUID>): List<Issue>

    @Query(
        value = "SELECT new pro.informatiq.shopscape.data.Issue(i.id, i.name, i.description, i.entityId, it.name) FROM IssueEntity i " +
                "JOIN MainEntity m ON m.id = i.entityId " +
                "JOIN IssueTypeEntity it ON i.status = it.id WHERE m.id = :id"
    )
    fun findAllIssuesWithTypesForEntity(id: UUID): List<Issue>

}