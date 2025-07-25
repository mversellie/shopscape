package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "requests_types")
data class RequestTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String
)