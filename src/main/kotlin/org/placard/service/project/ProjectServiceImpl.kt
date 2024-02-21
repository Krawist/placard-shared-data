package org.placard.service.project

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.project.Project
import org.placard.remote.ProjectCreationRequest
import org.placard.repositories.HierarchyRepository
import org.placard.repositories.ProjectRepository
import java.util.UUID

@Singleton
internal class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val hierarchyRepository: HierarchyRepository //TODO to be replace by hierarchyService
) : ProjectService {

    override fun createProject(projectCreationRequest: ProjectCreationRequest): HttpResponse<Project> {

        //TODO Take into account parent and unity of project name
        val hierarchy = hierarchyRepository.findById(projectCreationRequest.hierarchyUuid).orElseThrow {
            IllegalArgumentException()
        }

        val parent = projectCreationRequest.parentProjectUuid?.let {
            projectRepository.findById(projectCreationRequest.parentProjectUuid).orElseThrow {
                IllegalArgumentException()
            }
        }

        val project = Project(
            uuid = UUID.randomUUID(),
            displayName = projectCreationRequest.name,
            parentProjectUuid = parent?.uuid,
            hierarchyUuid = hierarchy.uuid
        )

        projectRepository.save(project)

        return HttpResponse.created(project)
    }

    override fun search(): HttpResponse<Page<Project>> {
        return HttpResponse.ok(projectRepository.findAll(Pageable.unpaged()))
    }
}