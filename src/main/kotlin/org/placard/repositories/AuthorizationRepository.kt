package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.Authorisation

@MongoRepository
internal interface AuthorizationRepository : PageableRepository<Authorisation, String>