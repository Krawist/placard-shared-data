package org.placard.web

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.placard.models.user.UserGroup
import org.placard.remote.UserGroupCreationRequest
import org.placard.service.user.UserGroupService

@Controller("user-groups")
internal class UserGroupController(
    private val userGroupService: UserGroupService
) {

    @Post
    fun createGroup(@Body userGroupCreationRequest: UserGroupCreationRequest) : HttpResponse<UserGroup>{
        return userGroupService.create(userGroupCreationRequest)
    }

    @Get("search")
    fun search() : HttpResponse<Page<UserGroup>>{
        return userGroupService.search()
    }

    @Post("{user_group_identifier}/add-users")
    fun addUsers(
        @PathVariable("user_group_identifier") userGroupIdentifier : String,
        @Body users : List<String>
    ) : HttpResponse<UserGroup>{
        return userGroupService.addUsers(userGroupIdentifier = userGroupIdentifier, usersIdentifier = users)
    }

    @Post("{user_group_identifier}/remove-users")
    fun removeUsers(
        @PathVariable("user_group_identifier") userGroupIdentifier : String,
        @Body users : List<String>
    ) : HttpResponse<UserGroup>{
        return userGroupService.removeUsers(userGroupIdentifier = userGroupIdentifier, usersIdentifier = users)
    }

}