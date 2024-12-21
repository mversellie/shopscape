package pro.informatiq.shopscape.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.data.Request
import pro.informatiq.shopscape.database.entities.RequestEntity
import java.util.*

@Repository
interface RequestsRepository : JpaRepository<RequestEntity, UUID> {
    @Query(
        value = "SELECT NEW pro.informatiq.shopscape.data.Request(r.id, r.name , r.description, r.entityId ,rt.name ) FROM RequestEntity r " +
                "JOIN RequestTypeEntity rt ON  r.status = rt.id",
    )
    fun findAllRequestsWithStatus(): List<Request>

     @Query(
        value = "SELECT NEW pro.informatiq.shopscape.data.Request(r.id, r.name , r.description, r.entityId ,rt.name ) FROM RequestEntity r " +
                "JOIN RequestTypeEntity rt ON  r.status = rt.id AND r.entityId IN :entities ",
    )
    fun findAllRequestsForEntities(entities: List<UUID>): List<Request>


     @Query(
        value = "SELECT NEW pro.informatiq.shopscape.data.Request(r.id, r.name , r.description, r.entityId ,rt.name ) FROM RequestEntity r " +
                "JOIN RequestTypeEntity rt ON  r.status = rt.id AND r.entityId = :id",
    )
    fun findAllRequestsForID(id: UUID): List<Request>
}
