package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.User
import java.util.Optional
import java.util.UUID

@Repository
internal interface UserRepository : PageableRepository<User, UUID> {

    fun findByEmailAddress(emailAddress : String) : Optional<User>

}