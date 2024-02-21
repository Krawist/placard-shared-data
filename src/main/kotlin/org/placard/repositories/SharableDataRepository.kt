package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.data.SharableData
import java.util.UUID

@MongoRepository
internal interface SharableDataRepository : PageableRepository<SharableData, UUID> {

/*    @Query(value = "SELECT data FROM " +
            "sharable_data data, accreditations accrediation, accreditation_items accreditationItem, hierarchy_items hierarchyItem " +
            "WHERE " +
            "data = accrediation.sharableData " +
            "and accrediation = accreditationItem.accreditation " +
            "and accreditationItem.hierarchyItem in :accreditationItems")
    fun getSharableDataByUserAccreditationLevelsAndItems(accreditationsLevels : List<Int>, accreditationItemsUuid : List<UUID>) : List<SharableData>*/

}