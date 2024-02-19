package org.placard.service.investigation

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.investigation.Investigation
import org.placard.remote.InvestigationDto

internal interface InvestigationService {

    fun create(investigationDto: InvestigationDto) : HttpResponse<Investigation>

    fun update(investigationDto: InvestigationDto) : HttpResponse<Investigation>

    fun search() : HttpResponse<Page<Investigation>>

    //TODO check investigation update
    //TODO check investigation step update
    //TODO check form update
    //TODO check how forms can be linked

}