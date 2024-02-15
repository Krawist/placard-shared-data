package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.Authorisation

@Repository
internal interface AuthorizationRepository : PageableRepository<Authorisation, String>