package org.placard.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.placard.models.access.Accreditation
import org.placard.remote.AccreditationCreationRequest
import org.placard.service.accreditation.AccreditationService
import java.util.UUID

@Controller("accreditations")
internal class AccreditationController(
    private val accreditationService: AccreditationService,
) {

    @Post("/abstract-user/{abstractUserUuid}")
    fun addAccreditationToUser(
        @PathVariable("abstractUserUuid") abstractUserUuid: UUID,
        @Body accreditations: List<AccreditationCreationRequest>,
    ) : HttpResponse<List<Accreditation>> {
        return accreditationService.addAccreditationsToUser(
            abstractUserUuid = abstractUserUuid,
            accreditations = accreditations
        )
    }

    @Post("/sharable-data/{dataIdentifier}")
    fun addAccreditationToData(
        @PathVariable("dataIdentifier") dataUuid: UUID,
        @Body accreditations: List<AccreditationCreationRequest>,
    ) : HttpResponse<List<Accreditation>> {
        return accreditationService.addAccreditationsToData(
            sharableDataUuid = dataUuid,
            accreditations = accreditations
        )
    }

}