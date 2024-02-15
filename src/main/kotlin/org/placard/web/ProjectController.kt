package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.placard.models.project.Project
import org.placard.remote.ProjectCreationRequest
import org.placard.service.project.ProjectService

@Controller("/projects")
internal class ProjectController(
    private val projectService: ProjectService
) {

    @Post
    fun create(@Body projectCreationRequest: ProjectCreationRequest) : HttpResponse<Project>{
        return projectService.createProject(projectCreationRequest)
    }

    @Get("/search")
    fun search() : HttpResponse<Page<Project>>{
        return projectService.search()
    }

}