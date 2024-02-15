package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.User
import java.util.Optional

@Repository
internal interface UserRepository : PageableRepository<User, String> {
    fun findByEmailAddress(emailAddress : String) : Optional<User>
}