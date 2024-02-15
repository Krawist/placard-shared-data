package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.placard.models.hierarchy.HierarchyItem
import org.placard.remote.HierarchyItemCreationRequest
import org.placard.service.hierarchy.HierarchyItemService

@Controller("hierarchy-items")
internal class HierarchyItemController(
    private val hierarchyItemService: HierarchyItemService
) {

    @Post
    fun createHierarchyItem(@Body hierarchyItemCreationRequest: HierarchyItemCreationRequest): HttpResponse<HierarchyItem> {
        return hierarchyItemService.create(hierarchyItemCreationRequest)
    }

    @Get("/search")
    fun search() : HttpResponse<Page<HierarchyItem>> {
        return hierarchyItemService.search()
    }

}