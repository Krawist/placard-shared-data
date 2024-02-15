package org.placard.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.placard.models.access.Accreditation
import org.placard.remote.AccreditationCreationRequest
import org.placard.service.accreditation.AccreditationService

@Controller("accreditations")
internal class AccreditationController(
    private val accreditationService: AccreditationService,
) {

    @Post("/abstract-user/{abstractUserUuid}")
    fun addAccreditationToUser(
        @PathVariable("abstractUserUuid") abstractUserUuid: String,
        @Body accreditations: List<AccreditationCreationRequest>,
    ) : HttpResponse<List<Accreditation>> {
        return accreditationService.addAccreditationsToUser(
            abstractUserUuid = abstractUserUuid,
            accreditations = accreditations
        )
    }

    @Post("/sharable-data/{dataIdentifier}")
    fun addAccreditationToData(
        @PathVariable("dataIdentifier") dataIdentifier: String,
        @Body accreditations: List<AccreditationCreationRequest>,
    ) : HttpResponse<List<Accreditation>> {
        return accreditationService.addAccreditationsToData(
            sharableDataUuid = dataIdentifier,
            accreditations = accreditations
        )
    }

}