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
import org.placard.models.investigation.Investigation
import org.placard.remote.InvestigationDto
import org.placard.service.investigation.InvestigationService
import java.util.UUID

@Controller(value = "investigations")
internal class InvestigationController(
    private val investigationService: InvestigationService,
) {

    @Post
    fun create(@Body investigationDto: InvestigationDto): HttpResponse<Investigation> {
        return investigationService.create(investigationDto = investigationDto)
    }

    @Put("/{investigationUuid}")
    fun update(
        @PathVariable("investigationUuid") investigationUuid: UUID,
        @Body investigationDto: InvestigationDto,
    ): HttpResponse<Investigation> {
        require(investigationDto.uuid == investigationUuid) {
            "Path uuid should be the name with uuid in model"
        }
        return investigationService.update(investigationDto = investigationDto)
    }

    @Get("/search")
    fun search(): HttpResponse<Page<Investigation>> {
        return investigationService.search()
    }

}