package org.placard.service.hierarchy

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.hierarchy.HierarchyItem
import org.placard.remote.HierarchyItemCreationRequest
import org.placard.repositories.HierarchyItemRepository
import org.placard.repositories.HierarchyRepository
import java.util.UUID

@Singleton
internal class HierarchyItemServiceImpl(
    private val hierarchyItemRepository: HierarchyItemRepository,
    private val hierarchyRepository: HierarchyRepository, //TODO use service
) : HierarchyItemService{

    override fun create(hierarchyItemCreationRequest: HierarchyItemCreationRequest): HttpResponse<HierarchyItem> {
        require(hierarchyItemCreationRequest.hierarchyIdentifier.isNotBlank()) { "Please provide the hierarchy item" }
        require(hierarchyItemCreationRequest.level > 0) { "Please provide a valid hierarchy item level" }
        require(hierarchyItemCreationRequest.displayName.isNotBlank()) { "Please the hierarchy item name" }

        val hierarchy = hierarchyRepository.findById(hierarchyItemCreationRequest.hierarchyIdentifier).orElseThrow {
            IllegalArgumentException("Provide hierarchy don't exist")
        }

        val parent = hierarchyItemCreationRequest.parentIdentifier?.let {
            hierarchyItemRepository.findById(it)?.orElseThrow {
                IllegalArgumentException("Hierarchy item parent provide don't exist")
            }
        }

        val hierarchyItem = HierarchyItem(
            identifier = UUID.randomUUID().toString(),
            level = hierarchyItemCreationRequest.level,
            name = hierarchyItemCreationRequest.displayName,
            hierarchy = hierarchy,
            parent = parent
        )

        hierarchyItemRepository.save(hierarchyItem)

        return HttpResponse.created(hierarchyItem)
    }

    override fun search(): HttpResponse<Page<HierarchyItem>> {
        return HttpResponse.ok(hierarchyItemRepository.findAll(Pageable.unpaged()))
    }
}