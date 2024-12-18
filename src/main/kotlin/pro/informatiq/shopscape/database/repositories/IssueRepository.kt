package pro.informatiq.shopscape.database.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.Database.entities.IssueEntity
import java.util.*

@Repository
interface IssueRepository : JpaRepository<IssueEntity, UUID> {

    @Query(
        value = "SELECT e.id AS entityId, e.name AS entityName, s.street_address, s.city, s.state, s.zip_code, s.phone_number, i.id AS issueId, i.name AS issueName, i.description AS issueDescription " +
                "FROM entities e " +
                "JOIN stores s ON e.id = s.entity_id " +
                "LEFT JOIN issues i ON e.id = i.entity_id " +
                "WHERE i.id IS NOT NULL",
        nativeQuery = true
    )
    fun findAllStoresWithIssues(): List<StoreWithIssuesProjection>


    @Query(
        value = "SELECT e.id AS entityId, e.name AS entityName, s.street_address, s.city, s.state, s.zip_code, s.phone_number, eq.serial_number, eq.description, eq.model_number, i.id AS issueId, i.name AS issueName, i.description AS issueDescription " +
                "FROM entities e " +
                "JOIN stores s ON e.id = s.entity_id " +
                "JOIN equipment eq ON e.id = eq.entity_id " +
                "LEFT JOIN issues i ON e.id = i.entity_id " +
                "WHERE i.id IS NOT NULL",
        nativeQuery = true
    )
    fun findAllStoresWithEquipmentThatHasIssues(): List<StoreWithEquipmentAndIssuesProjection>

    @Query(
        value = "SELECT e.id AS entityId, e.name AS entityName, s.street_address, s.city, s.state, s.zip_code, s.phone_number, " +
                "COUNT(i.id) AS issueCount, COUNT(DISTINCT eq.id) AS equipmentIssueCount " +
                "FROM entities e " +
                "JOIN stores s ON e.id = s.entity_id " +
                "LEFT JOIN issues i ON e.id = i.entity_id " +
                "LEFT JOIN equipment eq ON e.id = eq.entity_id " +
                "LEFT JOIN issues eqi ON eq.id = eqi.entity_id " +
                "GROUP BY e.id, e.name, s.street_address, s.city, s.state, s.zip_code, s.phone_number",
        nativeQuery = true
    )
    fun findAllStoresWithIssueCounts(): List<StoreWithIssueCountsProjection>

}

public interface StoreWithIssueCountsProjection {
        fun getEntityId(): UUID
        fun getEntityName(): String
        fun getStreetAddress(): String
        fun getCity(): String
        fun getState(): String
        fun getZipCode(): String
        fun getPhoneNumber(): String
        fun getIssueCount(): Long
        fun getEquipmentIssueCount(): Long
}

// Projections
public interface StoreWithIssuesProjection {
    fun getEntityId(): UUID
    fun getEntityName(): String
    fun getStreetAddress(): String
    fun getCity(): String
    fun getState(): String
    fun getZipCode(): String
    fun getPhoneNumber(): String
    fun getIssueId(): UUID?
    fun getIssueName(): String?
    fun getIssueDescription(): String?
}
public interface StoreWithEquipmentAndIssuesProjection {
    fun getEntityId(): UUID
    fun getEntityName(): String
    fun getStreetAddress(): String
    fun getCity(): String
    fun getState(): String
    fun getZipCode(): String
    fun getPhoneNumber(): String
    fun getSerialNumber(): String?
    fun getDescription(): String?
    fun getModelNumber(): String?
    fun getIssueId(): UUID?
    fun getIssueName(): String?
    fun getIssueDescription(): String?
}