package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import org.placard.models.investigation.InvestigationStep
import org.placard.remote.InvestigationStepDto
import org.placard.service.investigation.InvestigationStepService
import java.util.UUID

@Controller(value = "investigation-steps")
internal class InvestigationStepController(
    private val investigationStepService: InvestigationStepService,
) {
    @Post
    fun create(@Body investigationStepDto: InvestigationStepDto): HttpResponse<InvestigationStep> {
        return investigationStepService.create(investigationStepDto = investigationStepDto)
    }

    @Put("/{investigationStepUuid}")
    fun update(
        @PathVariable("investigationStepUuid") investigationStepUuid: UUID,
        @Body investigationStepDto: InvestigationStepDto,
    ): HttpResponse<InvestigationStep> {
        require(investigationStepDto.uuid == investigationStepUuid) {
            "Path uuid should be the name with uuid in model"
        }
        return investigationStepService.update(investigationStepDto = investigationStepDto)
    }

    @Get("/search")
    fun search(): HttpResponse<Page<InvestigationStep>> {
        return investigationStepService.search()
    }

}