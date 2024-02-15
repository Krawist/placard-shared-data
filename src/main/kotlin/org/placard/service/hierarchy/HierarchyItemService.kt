package org.placard.service.hierarchy

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.hierarchy.HierarchyItem
import org.placard.remote.HierarchyItemCreationRequest

internal interface HierarchyItemService {

    fun create(hierarchyItemCreationRequest: HierarchyItemCreationRequest) : HttpResponse<HierarchyItem>

    fun search() : HttpResponse<Page<HierarchyItem>>

}