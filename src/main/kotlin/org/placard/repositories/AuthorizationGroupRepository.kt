package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.AuthorisationGroup

@MongoRepository
internal interface AuthorizationGroupRepository : PageableRepository<AuthorisationGroup, String>