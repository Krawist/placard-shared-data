package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.user.AbstractUser
import java.util.UUID

@MongoRepository
internal interface AbstractUserRepository : PageableRepository<AbstractUser, UUID>