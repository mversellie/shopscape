package pro.informatiq.shopscape.entities

import org.springframework.stereotype.Service
import pro.informatiq.shopscape.database.entities.MainEntity
import pro.informatiq.shopscape.database.repositories.MainEntityRepository
import java.util.*
@Service
class MainEntityService(val mainEntityRepository: MainEntityRepository) {
    fun createMainEntity(name: String, entityTypeId: Int,newEntityId:UUID){
        val mainEntityToSave= MainEntity(newEntityId,name,entityTypeId)
        mainEntityRepository.save(mainEntityToSave)
    }
}