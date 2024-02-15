package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.UserGroup

@Repository
internal interface UserGroupRepository : PageableRepository<UserGroup, String>