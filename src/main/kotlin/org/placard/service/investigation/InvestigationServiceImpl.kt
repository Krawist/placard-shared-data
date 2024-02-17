package org.placard.service.investigation

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.investigation.Investigation
import org.placard.remote.InvestigationDto
import org.placard.repositories.InvestigationRepository
import org.placard.repositories.ProjectRepository
import java.util.UUID

@Singleton
internal class InvestigationServiceImpl(
    private val investigationRepository: InvestigationRepository,
    private val projectRepository: ProjectRepository,
) : InvestigationService {

    override fun create(investigationDto: InvestigationDto): HttpResponse<Investigation> {
        validateFields(investigationDto)

        val project = projectRepository.findById(investigationDto.projectUuid).get()

        val investigation = Investigation(displayName = investigationDto.displayName, project = project).also {
            it.uuid = UUID.randomUUID()
        }

        investigationRepository.save(investigation)

        return HttpResponse.created(investigation)
    }

    override fun update(investigationDto: InvestigationDto): HttpResponse<Investigation> {
        validateFields(investigationDto = investigationDto)

        val project = projectRepository.findById(investigationDto.projectUuid).get()

        val investigation = investigationRepository.findById(investigationDto.uuid).orElseThrow {
            IllegalArgumentException("Investigation with uuid ${investigationDto.uuid} not found")
        }.copy(displayName = investigationDto.displayName, project = project)

        investigationRepository.update(investigation)

        return HttpResponse.ok(investigation)
    }

    override fun search(): HttpResponse<Page<Investigation>> {
        return HttpResponse.ok(investigationRepository.findAll(Pageable.unpaged()))
    }

    private fun validateFields(investigationDto: InvestigationDto) {
        require(investigationDto.displayName.isNotBlank()) { "Investigation name cannot be blank" }

        require(projectRepository.existsById(investigationDto.projectUuid)) { "Project with uuid ${investigationDto.projectUuid} not found" }

        investigationRepository.findByDisplayNameIgnoreCaseAAndProject_Uuid(
            displayName = investigationDto.displayName,
            projectUUID = investigationDto.projectUuid
        ).ifPresent { investigation ->
            investigation.uuid?.let {
                require(it == investigationDto.uuid)
            }
        }
    }
}