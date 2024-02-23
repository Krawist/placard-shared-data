package org.placard.service.form

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.form.Form
import org.placard.models.form.FormEventType
import org.placard.remote.FormDto
import org.placard.repositories.FormRepository
import org.placard.repositories.InvestigationRepository
import org.placard.repositories.InvestigationStepRepository
import java.util.UUID

@Singleton
internal class FormServiceImpl(
    private val formRepository: FormRepository,
    private val investigationRepository: InvestigationRepository,
    private val investigationStepRepository: InvestigationStepRepository,
) : FormService {

    override fun create(formDto: FormDto): HttpResponse<Form> {
        validateFields(formDto = formDto)

        val form = Form(
            uuid = UUID.randomUUID(),
            displayName = formDto.displayName,
            investigationUuid = formDto.investigationUuid,
            investigationStepUuid = formDto.investigationStepUuid,
            formStatus = formDto.formStatus,
            eventType = formDto.eventType
        )

        formRepository.save(form)

        return HttpResponse.created(form)
    }

    override fun update(formDto: FormDto): HttpResponse<Form> {

        validateFields(formDto = formDto)

        val form = formRepository.findById(formDto.uuid).orElseThrow {
            IllegalArgumentException("Form with uuid ${formDto.uuid} not found")
        }.copy(
            displayName = formDto.displayName,
            investigationUuid = formDto.investigationUuid,
            investigationStepUuid = formDto.investigationStepUuid
        )

        formRepository.update(form)

        return HttpResponse.ok(form)

    }

    override fun search(): HttpResponse<Page<Form>> {
        return HttpResponse.ok(formRepository.findAll(Pageable.unpaged()))
    }

    private fun validateFields(formDto: FormDto) {

        require(formDto.displayName.isNotBlank()) { "Form name cannot be blank" }

        when (formDto.eventType) {

            FormEventType.CAPTURE_EVENT -> {
                requireNotNull(formDto.investigationUuid) { "For one-time event the investigation uuid is required" }

                require(formDto.investigationStepUuid == null) {"For tracking event, Investigation step should not be provided"}

                formDto.investigationUuid.let { investigationUuid ->
                    require(investigationRepository.existsById(investigationUuid)) { "Investigation with uuid $investigationUuid not found" }

                    formRepository.findByDisplayNameIgnoreCaseAndInvestigationUuid(
                        displayName = formDto.displayName,
                        investigationUuid = investigationUuid
                    ).ifPresent { form ->
                        require(form.uuid == formDto.uuid) {"A form with the name ${formDto.displayName} already exist in this investigation"}
                    }
                }
            }

            FormEventType.TRACKING_EVENT -> {
                requireNotNull(formDto.investigationStepUuid) { "For tracking event, the investigation step uuid is required" }

                require(formDto.investigationUuid == null) { "For tracking event the project should not be provided" }

                require(investigationStepRepository.existsById(formDto.investigationStepUuid)) { "Step with uuid ${formDto.investigationStepUuid} not found" }

                formRepository.findByDisplayNameIgnoreCaseAndInvestigationStepUuid(
                    displayName = formDto.displayName,
                    investigationStepUuid = formDto.investigationStepUuid
                ).ifPresent { form ->
                    require(form.uuid == formDto.uuid) {"A form with the name ${formDto.displayName} already exist in this investigation step"}
                }
            }
        }

    }
}