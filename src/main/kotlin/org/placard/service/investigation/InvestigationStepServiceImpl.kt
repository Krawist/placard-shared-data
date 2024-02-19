package org.placard.service.investigation

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.investigation.Investigation
import org.placard.models.investigation.InvestigationStep
import org.placard.remote.InvestigationStepDto
import org.placard.repositories.InvestigationRepository
import org.placard.repositories.InvestigationStepRepository
import java.util.UUID

@Singleton
internal class InvestigationStepServiceImpl(
    private val investigationStepRepository: InvestigationStepRepository,
    private val investigationRepository: InvestigationRepository,
) : InvestigationStepService {

    override fun create(investigationStepDto: InvestigationStepDto): HttpResponse<InvestigationStep> {
        validateFields(investigationStepDto = investigationStepDto)

        val investigationStep = InvestigationStep(
            displayName = investigationStepDto.displayName,
            investigation = investigationRepository.findById(investigationStepDto.investigationUuid).get()
        ).also {
            it.uuid = UUID.randomUUID()
        }

        return HttpResponse.created(investigationStepRepository.save(investigationStep))
    }

    override fun update(investigationStepDto: InvestigationStepDto): HttpResponse<InvestigationStep> {
        validateFields(investigationStepDto = investigationStepDto)

        val investigationStep = investigationStepRepository.findById(investigationStepDto.uuid).orElseThrow {
            IllegalArgumentException("Step with uuid ${investigationStepDto.uuid} not found")
        }.copy(
            displayName = investigationStepDto.displayName,
            investigation = investigationRepository.findById(investigationStepDto.investigationUuid).get()
        )

        return HttpResponse.ok(investigationStepRepository.update(investigationStep))
    }

    override fun search(): HttpResponse<Page<InvestigationStep>> {
        return HttpResponse.ok(investigationStepRepository.findAll(Pageable.unpaged()))
    }

    private fun validateFields(investigationStepDto: InvestigationStepDto) {

        require(investigationStepDto.displayName.isNotBlank()) { "Step name cannot be blank" }

        require(investigationRepository.existsById(investigationStepDto.investigationUuid)) {
            "Investigation with uuid ${investigationStepDto.investigationUuid} not found"
        }

        investigationStepRepository.findByDisplayNameIgnoreCaseAndInvestigation_Uuid(
            displayName = investigationStepDto.displayName,
            investigationUUID = investigationStepDto.investigationUuid
        ).ifPresent { investigation ->
            investigation.uuid?.let {
                require(it == investigationStepDto.uuid) { "A Step with this name already exist in this investigation" }
            }
        }

    }
}