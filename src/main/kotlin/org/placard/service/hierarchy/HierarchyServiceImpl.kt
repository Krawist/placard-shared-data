package org.placard.service.hierarchy

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.hierarchy.HierarchyItem
import org.placard.models.hierarchy.Hierarchy
import org.placard.remote.HierarchyCreationRequest
import org.placard.remote.HierarchyItemCreationRequest
import org.placard.repositories.HierarchyItemRepository
import org.placard.repositories.HierarchyRepository
import java.util.UUID

@Singleton
internal class HierarchyServiceImpl(
    private val hierarchyRepository: HierarchyRepository,
    private val hierarchyItemRepository: HierarchyItemRepository,
) : HierarchyService {

    override fun create(hierarchyCreationRequest: HierarchyCreationRequest): HttpResponse<Hierarchy> {
        val hierarchy = Hierarchy(
            identifier = UUID.randomUUID().toString(),
            name = hierarchyCreationRequest.name
        )
        hierarchyRepository.save(hierarchy)

        return HttpResponse.created(hierarchy)
    }

    override fun search(): HttpResponse<Page<Hierarchy>> {
        return HttpResponse.ok(hierarchyRepository.findAll(Pageable.unpaged()))
    }
}