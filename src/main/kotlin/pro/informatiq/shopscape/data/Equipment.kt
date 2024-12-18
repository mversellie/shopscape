package pro.informatiq.shopscape.data
import java.util.*
data class Equipment(
    val serialNumber: String?,
    val description: String?,
    val modelNumber: String?,
    val idIn: UUID,
    var requestsIn: List<Request>? = emptyList(),
    var issuesIn: List<Issue>? = emptyList(),
    var nameIn:String
) : MainEntityResponse( issues=issuesIn, requests = requestsIn, name = nameIn,id=idIn)