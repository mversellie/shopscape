package pro.informatiq.shopscape.data

import java.util.*

abstract class MainEntityResponse(
    val id: UUID,
    val name: String,
    val requests: List<Request>?,
    val issues: List<Issue>?
    )