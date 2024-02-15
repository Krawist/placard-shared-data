package org.placard.service.accreditation

import io.micronaut.http.HttpResponse
import org.placard.models.access.Accreditation
import org.placard.remote.AccreditationCreationRequest

internal interface AccreditationService {
    fun addAccreditationsToUser(abstractUserUuid : String, accreditations : List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>>

    fun addAccreditationsToData(sharableDataUuid : String, accreditations : List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>>

    fun removeAccreditations(accreditations : List<String>) : HttpResponse<Nothing>

}