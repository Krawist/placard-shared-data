package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.User
import java.util.Optional
import java.util.UUID

@MongoRepository
internal interface UserRepository : PageableRepository<User, UUID> {

    fun findByEmailAddress(emailAddress : String) : Optional<User>

}