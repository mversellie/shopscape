package pro.informatiq.shopscape.data

import java.util.*

data class Issue(
    val id: UUID,
    val name: String = "",
    val description: String = "",
    val entityId: UUID,
    val status: String = "Invalid"
)