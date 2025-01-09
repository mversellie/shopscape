package pro.informatiq.shopscape.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "entity_type")
data class EntityTypeEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    var name: String)