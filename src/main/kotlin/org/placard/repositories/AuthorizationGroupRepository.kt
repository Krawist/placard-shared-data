package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.AuthorisationGroup

@Repository
internal interface AuthorizationGroupRepository : PageableRepository<AuthorisationGroup, String>