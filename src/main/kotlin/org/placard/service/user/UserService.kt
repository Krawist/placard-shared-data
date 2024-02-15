package org.placard.service.user

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.user.User
import org.placard.remote.UserCreationRequest

internal interface UserService {

    fun create(userCreationRequest: UserCreationRequest) : HttpResponse<User>

    fun search() : HttpResponse<Page<User>>

}