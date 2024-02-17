package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.Role
import java.util.UUID

@Repository
internal interface RoleRepository : PageableRepository<Role, UUID>