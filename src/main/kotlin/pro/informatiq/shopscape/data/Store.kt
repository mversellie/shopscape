package pro.informatiq.shopscape.data
import pro.informatiq.shopscape.database.entities.StoreEntity
import java.util.*
data class Store(
    override var id: UUID,
    override var name: String,
    val streetAddress: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val phoneNumber: String,
    var equipment: MutableList<Equipment>? = mutableListOf(),
    override var requests: List<Request>? = mutableListOf(),
    override var issues: List<Issue>? = mutableListOf(),
) : MainEntityResponse()