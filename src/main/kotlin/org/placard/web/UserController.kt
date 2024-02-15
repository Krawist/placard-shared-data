package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.placard.models.user.User
import org.placard.remote.UserCreationRequest
import org.placard.service.user.UserService

@Controller("/users")
internal class UserController(
    private val userService: UserService
) {

    @Post
    fun create(@Body userCreationRequest: UserCreationRequest) : HttpResponse<User>{
        return userService.create(userCreationRequest)
    }

    @Get("/search")
    fun search() : HttpResponse<Page<User>>{
        return userService.search()
    }

}