package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*


@Entity
@Table(name = "issues_types")
data class IssueTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String
)