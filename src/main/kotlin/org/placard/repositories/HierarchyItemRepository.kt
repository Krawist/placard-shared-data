package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository
import org.placard.models.hierarchy.HierarchyItem

@Repository
internal interface HierarchyItemRepository : PageableRepository<HierarchyItem, String> {
    fun findByParent(hierarchyItem: HierarchyItem?, pageable: Pageable) : Page<HierarchyItem>

    fun findByParent(hierarchyItem: HierarchyItem?) : List<HierarchyItem>
}