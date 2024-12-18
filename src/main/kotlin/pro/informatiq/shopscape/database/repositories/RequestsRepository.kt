package pro.informatiq.shopscape.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository
import pro.informatiq.shopscape.database.entities.RequestEntity
import java.util.*

@Repository
public interface RequestsRepository : JpaRepository<RequestEntity, UUID> {
    @Query(
            value = "SELECT e.id AS entityId, e.name AS entityName, s.street_address, s.city, s.state, s.zip_code, s.phone_number, r.id AS requestId, r.name AS requestName, r.description AS requestDescription " +
                    "FROM entities e " +
                    "JOIN stores s ON e.id = s.entity_id " +
                    "LEFT JOIN requests r ON e.id = r.entity_id " +
                    "WHERE r.id IS NOT NULL",
            nativeQuery = true
    )
    fun findAllStoresWithRequests():List<StoreWithRequestsProjection>
    @Query(
            value = "SELECT e.id AS entityId, e.name AS entityName, s.street_address, s.city, s.state, s.zip_code, s.phone_number, eq.serial_number, eq.description, eq.model_number, r.id AS requestId, r.name AS requestName, r.description AS requestDescription " +
                    "FROM entities e " +
                    "JOIN stores s ON e.id = s.entity_id " +
                    "JOIN equipment eq ON e.id = eq.entity_id " +
                    "LEFT JOIN requests r ON e.id = r.entity_id " +
                    "WHERE r.id IS NOT NULL",
            nativeQuery = true
    )
    fun findAllStoresWithEquipmentThatHasRequests():List<StoreWithEquipmentAndRequestsProjection>

    @Query(
            value = "SELECT e.id AS entityId, e.name AS entityName, s.street_address, s.city, s.state, s.zip_code, s.phone_number, " +
                    "COUNT(r.id) AS requestCount, COUNT(DISTINCT eq.id) AS equipmentRequestCount " +
                    "FROM entities e " +
                    "JOIN stores s ON e.id = s.entity_id " +
                    "LEFT JOIN requests r ON e.id = r.entity_id " +
                    "LEFT JOIN equipment eq ON e.id = eq.entity_id " +
                    "LEFT JOIN requests eqr ON eq.id = eqr.entity_id " +
                    "GROUP BY e.id, e.name, s.street_address, s.city, s.state, s.zip_code, s.phone_number",
            nativeQuery = true
    )
    fun findAllStoresWithRequestCounts():List<StoreWithRequestCountsProjection>
}
    interface StoreWithRequestCountsProjection {
        fun getEntityId(): UUID
        fun getEntityName(): String
        fun getStreetAddress(): String
        fun getCity(): String
        fun getState(): String
        fun getZipCode(): String
        fun getPhoneNumber(): String
        fun getRequestCount(): Long
        fun getEquipmentRequestCount(): Long
    }

    // Projections for requests
    interface StoreWithRequestsProjection {
        fun getEntityId(): UUID
        fun getEntityName(): String
        fun getStreetAddress(): String
        fun getCity(): String
        fun getState(): String
        fun getZipCode(): String
        fun getPhoneNumber(): String
        fun getRequestId(): UUID?
        fun getRequestName(): String?
        fun getRequestDescription(): String?
    }
    interface StoreWithEquipmentAndRequestsProjection {
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
        fun getRequestId(): UUID?
        fun getRequestName(): String?
        fun getRequestDescription(): String?
    }
