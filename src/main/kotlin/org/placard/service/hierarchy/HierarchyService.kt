package org.placard.service.hierarchy

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.hierarchy.Hierarchy
import org.placard.remote.HierarchyCreationRequest

internal interface HierarchyService {

    fun create(hierarchyCreationRequest: HierarchyCreationRequest) : HttpResponse<Hierarchy>

    fun search() : HttpResponse<Page<Hierarchy>>

}