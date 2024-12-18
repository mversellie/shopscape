package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*
import java.util.*
import pro.informatiq.shopscape.data.Store

@Entity
@Table(name = "stores")
data class StoreEntity(
    @Id
    @Column(name = "entity_id",nullable = false)
    val entityId: UUID = UUID.randomUUID(),

    @Column(name = "street_address")
    var streetAddress: String,

    @Column(name = "city")
    var city: String,

    @Column(name = "state")
    var state: String,
    @Column(name = "zip_code")
    var zipCode: String,
    @Column(name = "phone_number")
    var phoneNumber: String,
){
    fun toStorePojo(name:String): Store {
            return Store(
                idIn = this.entityId,
                streetAddress = this.streetAddress,
                city = this.city,
                state = this.state,
                zipCode = this.zipCode,
                phoneNumber = this.phoneNumber,
                nameIn = name
            )
    }
}