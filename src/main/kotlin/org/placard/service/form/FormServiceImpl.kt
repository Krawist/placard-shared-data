package org.placard.service.form

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.form.Form
import org.placard.remote.FormDto
import org.placard.repositories.FormRepository
import org.placard.repositories.InvestigationStepRepository
import org.placard.repositories.ProjectRepository
import java.util.UUID

@Singleton
internal class FormServiceImpl(
    private val formRepository: FormRepository,
    private val projectRepository: ProjectRepository,
    private val investigationStepRepository: InvestigationStepRepository
) : FormService {

    override fun create(formDto: FormDto): HttpResponse<Form> {
        validateFields(formDto = formDto)

        val project = formDto.projectUuid?.let {
            projectRepository.findById(formDto.projectUuid).get()
        }

        val investigationStep = formDto.investigationStepUUID?.let {
            investigationStepRepository.findById(formDto.investigationStepUUID).get()
        }

        val form = Form(
            displayName = formDto.displayName,
            project = project,
            investigationStep = investigationStep
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

        val investigationStep = formDto.investigationStepUUID?.let {
            investigationStepRepository.findById(formDto.investigationStepUUID).get()
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

    private fun validateFields(formDto: FormDto){

        require(formDto.displayName.isNotBlank()) {"Form name cannot be blank"}

        require(!(formDto.projectUuid != null && formDto.investigationStepUUID != null)) {"The form should be attached to a project or an investigation step"}

        formDto.projectUuid?.let { projectUuid ->
            require(projectRepository.existsById(projectUuid)) {"Project with uuid $projectUuid not found"}

            formRepository.findByDisplayNameIgnoreCaseAndProject_Uuid(displayName = formDto.displayName, projectUUID = projectUuid).ifPresent { form ->
                formDto.uuid?.let {
                    require(form.uuid == it)
                }
            }
        }

        formDto.investigationStepUUID?.let { investigationStepUuid ->
            require(investigationStepRepository.existsById(investigationStepUuid)) {"Step with uuid $investigationStepUuid not found"}

            formRepository.findByDisplayNameIgnoreCaseAndInvestigationStep_Uuid(displayName = formDto.displayName, investigationStepUuid = investigationStepUuid).ifPresent { form ->
                formDto.uuid?.let {
                    require(form.uuid == it)
                }
            }
        }



    }
}