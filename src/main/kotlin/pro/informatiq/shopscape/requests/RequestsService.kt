package pro.informatiq.shopscape.requests

import org.springframework.stereotype.Service
import pro.informatiq.shopscape.data.Request
import pro.informatiq.shopscape.database.repositories.RequestsRepository
import java.util.*
@Service
class RequestService(val requestRepository: RequestsRepository) {
    data class StoreRequestSummary(
        val id: UUID,
        val name: String,
        val streetAddress: String,
        val city: String,
        val state: String,
        val zipCode: String,
        val phoneNumber: String,
        val requestCount: Long,
        val equipmentRequestCount: Long
    )
    /*
    fun getStoreRequestSummaries(): List<StoreRequestSummary> {
        return requestRepository.findAllStoresWithRequestCounts().map { projection ->
            StoreRequestSummary(
                id = projection.getEntityId(),
                name = projection.getEntityName(),
                streetAddress = projection.getStreetAddress(),
                city = projection.getCity(),
                state = projection.getState(),
                zipCode = projection.getZipCode(),
                phoneNumber = projection.getPhoneNumber(),
                requestCount = projection.getRequestCount(),
                equipmentRequestCount = projection.getEquipmentRequestCount()
            )
        }
    }
    */

    fun getAllRequestsWithStatus(): List<Request> {
        return requestRepository.findAllRequestsWithStatus()
    }


    fun getTotalRequestCount(): Long {
        return requestRepository.count()
    }

    fun getAllRequestsForEntities(entities: List<UUID>): List<Request> {
        return requestRepository.findAllRequestsForEntities(entities)
    }
    fun getAllRequestsForID(id: UUID): List<Request> {
        return requestRepository.findAllRequestsForID(id)
    }
}