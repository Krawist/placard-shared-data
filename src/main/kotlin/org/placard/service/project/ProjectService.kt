package org.placard.service.project

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.project.Project
import org.placard.remote.ProjectCreationRequest

internal interface ProjectService {
    fun createProject(projectCreationRequest: ProjectCreationRequest) : HttpResponse<Project>

    fun search() : HttpResponse<Page<Project>>
}