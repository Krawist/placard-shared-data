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
import java.util.UUID

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

    @Post("{userGroupIdentifier}/add-users")
    fun addUsers(
        @PathVariable("userGroupIdentifier") userGroupUuid : UUID,
        @Body usersUuid : List<UUID>
    ) : HttpResponse<UserGroup>{
        return userGroupService.addUsers(userGroupUuid = userGroupUuid, usersUuid = usersUuid)
    }

    @Post("{userGroupIdentifier}/remove-users")
    fun removeUsers(
        @PathVariable("userGroupIdentifier") userGroupUuid : UUID,
        @Body usersUuid : List<UUID>
    ) : HttpResponse<UserGroup>{
        return userGroupService.removeUsers(userGroupUuid = userGroupUuid, usersUuid = usersUuid)
    }

}