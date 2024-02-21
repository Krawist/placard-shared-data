package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.AuthorisationGroup
import java.util.UUID

@MongoRepository
internal interface AuthorizationGroupRepository : PageableRepository<AuthorisationGroup, UUID>