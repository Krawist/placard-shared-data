package org.placard.repositories

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository
import org.placard.models.data.SharableData
import org.placard.models.hierarchy.HierarchyItem

@Repository
internal interface SharableDataRepository : PageableRepository<SharableData, String> {

    @Query(value = "SELECT data FROM " +
            "sharable_data data, accreditations accrediation, accreditation_items accreditationItem, hierarchy_items hierarchyItem " +
            "WHERE " +
            "data = accrediation.sharableData " +
            "and accrediation = accreditationItem.accreditation " +
            "and accreditationItem.hierarchyItem in :accreditationItems")
    fun getSharableDataByUserAccreditationLevelsAndItems(accreditationsLevels : List<Int>, accreditationItems : List<HierarchyItem>) : List<SharableData>

}