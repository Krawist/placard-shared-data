package org.placard.service.investigation

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.investigation.InvestigationStep
import org.placard.remote.InvestigationStepDto

internal interface InvestigationStepService {

    fun create(investigationStepDto: InvestigationStepDto) : HttpResponse<InvestigationStep>

    fun update(investigationStepDto: InvestigationStepDto) : HttpResponse<InvestigationStep>

    fun search() : HttpResponse<Page<InvestigationStep>>

}