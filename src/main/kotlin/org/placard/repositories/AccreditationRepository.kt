package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.access.Accreditation
import java.util.UUID

@MongoRepository
internal interface AccreditationRepository : PageableRepository<Accreditation, UUID> {

    fun findByAbstractUserUuid(abstractUserUuid: UUID) : List<Accreditation>

    fun findByAbstractUserUuidInList(abstractUsers: List<UUID>) : List<Accreditation>

    fun deleteByAbstractUserUuid(abstractUserUuid: UUID)
}