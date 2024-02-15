package org.placard.service.user

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.user.UserGroup
import org.placard.remote.UserGroupCreationRequest

internal interface UserGroupService {

    fun create(userGroupCreationRequest: UserGroupCreationRequest) : HttpResponse<UserGroup>

    fun search() : HttpResponse<Page<UserGroup>>

    fun addUsers(usersIdentifier : List<String>, userGroupIdentifier : String) : HttpResponse<UserGroup>

    fun removeUsers(usersIdentifier : List<String>, userGroupIdentifier: String) : HttpResponse<UserGroup>

}