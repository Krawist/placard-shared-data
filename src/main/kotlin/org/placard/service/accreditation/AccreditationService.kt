package org.placard.service.accreditation

import io.micronaut.http.HttpResponse
import org.placard.models.access.Accreditation
import org.placard.remote.AccreditationCreationRequest
import java.util.UUID

internal interface AccreditationService {

    fun addAccreditationsToUser(abstractUserUuid : UUID, accreditations : List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>>

    fun addAccreditationsToData(sharableDataUuid : UUID, accreditations : List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>>

    fun removeAccreditations(accreditations : List<UUID>) : HttpResponse<Nothing>

}