package org.placard.service.user

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.user.UserGroup
import org.placard.remote.UserGroupCreationRequest
import java.util.UUID

internal interface UserGroupService {

    fun create(userGroupCreationRequest: UserGroupCreationRequest) : HttpResponse<UserGroup>

    fun search() : HttpResponse<Page<UserGroup>>

    fun addUsers(usersUuid : List<UUID>, userGroupUuid : UUID) : HttpResponse<UserGroup>

    fun removeUsers(usersUuid : List<UUID>, userGroupUuid: UUID) : HttpResponse<UserGroup>

}