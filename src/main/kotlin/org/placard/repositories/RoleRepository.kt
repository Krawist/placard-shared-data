package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.roles.Role

@Repository
interface RoleRepository : PageableRepository<Role, String>