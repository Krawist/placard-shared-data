package org.placard.service.form

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.form.Form
import org.placard.models.form.FormEventType
import org.placard.remote.FormDto
import org.placard.repositories.FormRepository
import org.placard.repositories.InvestigationStepRepository
import org.placard.repositories.ProjectRepository
import java.util.UUID

@Singleton
internal class FormServiceImpl(
    private val formRepository: FormRepository,
    private val projectRepository: ProjectRepository,
    private val investigationStepRepository: InvestigationStepRepository,
) : FormService {

    override fun create(formDto: FormDto): HttpResponse<Form> {
        validateFields(formDto = formDto)

        val project = formDto.projectUuid?.let {
            projectRepository.findById(formDto.projectUuid).get()
        }

        val investigationStep = formDto.investigationStepUuid?.let {
            investigationStepRepository.findById(formDto.investigationStepUuid).get()
        }

        val form = Form(
            displayName = formDto.displayName,
            project = project,
            investigationStep = investigationStep,
            eventType = formDto.eventType
        ).also {
            it.uuid = UUID.randomUUID()
        }

        formRepository.save(form)

        return HttpResponse.created(form)
    }

    override fun update(formDto: FormDto): HttpResponse<Form> {

        validateFields(formDto = formDto)

        val project = formDto.projectUuid?.let {
            projectRepository.findById(formDto.projectUuid).get()
        }

        val investigationStep = formDto.investigationStepUuid?.let {
            investigationStepRepository.findById(formDto.investigationStepUuid).get()
        }

        val form = formRepository.findById(formDto.uuid).orElseThrow {
            IllegalArgumentException("Form with uuid ${formDto.uuid} not found")
        }.copy(
            displayName = formDto.displayName,
            project = project,
            investigationStep = investigationStep
        )

        formRepository.update(form)

        return HttpResponse.ok(form)

    }

    override fun search(): HttpResponse<Page<Form>> {
        return HttpResponse.ok(formRepository.findAll(Pageable.unpaged()))
    }

    private fun validateFields(formDto: FormDto) {

        require(formDto.displayName.isNotBlank()) { "Form name cannot be blank" }

        when(formDto.eventType){

            FormEventType.CAPTURE_EVENT -> {
                requireNotNull(formDto.projectUuid) {"For one-time event the project uuid is required"}

                require(formDto.investigationStepUuid == null) {"For one-time event the investigation step should not be provided"}

                require(projectRepository.existsById(formDto.projectUuid)) { "Project with uuid ${formDto.projectUuid} not found" }

                formRepository.findByDisplayNameIgnoreCaseAndProject_Uuid(
                    displayName = formDto.displayName,
                    projectUUID = formDto.projectUuid
                ).ifPresent { form ->
                    formDto.uuid?.let {
                        require(form.uuid == it)
                    }
                }
            }

            FormEventType.TRACKING_EVENT -> {
                requireNotNull(formDto.investigationStepUuid) {"For tracking event, the investigation step uuid is required"}

                require(formDto.projectUuid == null) {"For tracking event the project should not be provided"}

                require(investigationStepRepository.existsById(formDto.investigationStepUuid)) { "Step with uuid ${formDto.investigationStepUuid} not found" }

                formRepository.findByDisplayNameIgnoreCaseAndInvestigationStep_Uuid(
                    displayName = formDto.displayName,
                    investigationStepUuid = formDto.investigationStepUuid
                ).ifPresent { form ->
                    formDto.uuid?.let {
                        require(form.uuid == it)
                    }
                }
            }
        }

    }
}