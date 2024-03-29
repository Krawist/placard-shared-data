package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.investigation.Investigation
import java.util.Optional
import java.util.UUID

@MongoRepository
internal interface InvestigationRepository : PageableRepository<Investigation, UUID> {

    fun findByDisplayNameIgnoreCaseAndProjectUuid(displayName: String, projectUuid: UUID) : Optional<Investigation>

}