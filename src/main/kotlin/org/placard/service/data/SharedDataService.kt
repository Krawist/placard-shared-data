package org.placard.service.data

import io.micronaut.http.HttpResponse
import org.placard.models.data.SharableData
import org.placard.remote.DataToUpload
import org.placard.remote.SearchQueryDescription

internal interface SharedDataService {

    fun uploadData(dataToUpload: DataToUpload) : HttpResponse<SharableData>

    fun search(searchQueryDescription: SearchQueryDescription) : HttpResponse<List<SharableData>>

}