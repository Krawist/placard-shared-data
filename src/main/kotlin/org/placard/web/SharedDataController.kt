package org.placard.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.placard.models.data.SharableData
import org.placard.remote.DataToUpload
import org.placard.remote.SearchQueryDescription
import org.placard.service.data.SharedDataService

@Controller("/shared-data")
internal class SharedDataController(
    private val sharedDataService: SharedDataService
) {

    @Post
    fun uploadData(@Body dataToUpload: DataToUpload) : HttpResponse<SharableData>{
        return sharedDataService.uploadData(dataToUpload)
    }

    @Post("/search")
    fun search(@Body searchQueryDescription: SearchQueryDescription) : HttpResponse<List<SharableData>>{
        //TODO check here if the requester is the one provide in the searchQueryDescription.searchBy
        return sharedDataService.search(searchQueryDescription = searchQueryDescription)
    }

}