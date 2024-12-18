package pro.informatiq.shopscape.data
import pro.informatiq.shopscape.Database.Entities.StoreEntity
import java.util.*
data class Store(
    var idIn: UUID,
    val streetAddress: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val phoneNumber: String,
    var requestsIn: MutableList<Request>? = mutableListOf(),
    var issuesIn: MutableList<Issue> = mutableListOf(),
    var nameIn:String,
    val equipment: MutableList<Equipment> = mutableListOf(),
) : MainEntityResponse(id = idIn,name= nameIn,requests=requestsIn, issues=issuesIn){
    fun toEntity(): StoreEntity {
        return StoreEntity(
            entityId = id,
            streetAddress = streetAddress,
            city = city,
            state = state,
            zipCode = zipCode,
            phoneNumber = phoneNumber,
        )
    }
}