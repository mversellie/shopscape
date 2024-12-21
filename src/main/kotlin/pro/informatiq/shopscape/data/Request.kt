package pro.informatiq.shopscape.data

import java.util.*

data class Request(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val description: String = "",
    val entityId: UUID = UUID.randomUUID(),
    val status:String ="Invalid"
)