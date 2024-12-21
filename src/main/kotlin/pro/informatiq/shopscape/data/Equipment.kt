package pro.informatiq.shopscape.data
import java.util.*
data class Equipment(
    val serialNumber: String?,
    val description: String?,
    val modelNumber: String?,
    override val id: UUID,
    override var name:String,
    override var issues: List<Issue>? = null,
    override var requests: List<Request>? = null
) : MainEntityResponse()