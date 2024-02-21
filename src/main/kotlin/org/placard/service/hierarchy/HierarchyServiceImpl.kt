package org.placard.service.hierarchy

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.hierarchy.Hierarchy
import org.placard.remote.HierarchyCreationRequest
import org.placard.repositories.HierarchyItemRepository
import org.placard.repositories.HierarchyRepository
import java.util.UUID

@Singleton
internal class HierarchyServiceImpl(
    private val hierarchyRepository: HierarchyRepository,
) : HierarchyService {

    override fun create(hierarchyCreationRequest: HierarchyCreationRequest): HttpResponse<Hierarchy> {
        val hierarchy = Hierarchy(
            uuid = UUID.randomUUID(),
            displayName = hierarchyCreationRequest.name
        )
        hierarchyRepository.save(hierarchy)

        return HttpResponse.created(hierarchy)
    }

    override fun search(): HttpResponse<Page<Hierarchy>> {
        return HttpResponse.ok(hierarchyRepository.findAll(Pageable.unpaged()))
    }
}