package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.UserGroup
import java.util.UUID

@MongoRepository
internal interface UserGroupRepository : PageableRepository<UserGroup, UUID>