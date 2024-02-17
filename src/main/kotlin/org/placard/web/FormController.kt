package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.placard.models.form.Form
import org.placard.models.investigation.Investigation
import org.placard.remote.FormDto
import org.placard.service.form.FormService
import java.util.UUID

@Controller(value = "forms")
internal class FormController(
    private val formService: FormService,
) {

    @Post
    fun create(@Body formDto: FormDto): HttpResponse<Form> {
        return formService.create(formDto = formDto)
    }

    @Patch("/{formUuid}")
    fun update(
        @PathVariable("formUuid") formUuid: UUID,
        @Body formDto: FormDto,
    ): HttpResponse<Form> {
        require(formDto.uuid == formUuid) {
            "Path uuid should be the name with uuid in model"
        }
        return formService.update(formDto = formDto)
    }

    @Get("/search")
    fun search(): HttpResponse<Page<Form>> {
        return formService.search()
    }

}