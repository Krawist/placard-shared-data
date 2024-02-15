package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.placard.models.hierarchy.HierarchyItem
import org.placard.models.hierarchy.Hierarchy
import org.placard.remote.HierarchyCreationRequest
import org.placard.remote.HierarchyItemCreationRequest
import org.placard.service.hierarchy.HierarchyService

@Controller("/hierarchies")
internal class HierarchyController(
    private val hierarchyService: HierarchyService,
) {

    @Post
    fun create(@Body hierarchyCreationRequest: HierarchyCreationRequest): HttpResponse<Hierarchy> {
        return hierarchyService.create(hierarchyCreationRequest)
    }

    @Get("/search")
    fun search(): HttpResponse<Page<Hierarchy>> {
        return hierarchyService.search()
    }
}