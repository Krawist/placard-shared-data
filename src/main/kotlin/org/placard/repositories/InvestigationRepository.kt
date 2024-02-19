package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.investigation.Investigation
import java.util.Optional
import java.util.UUID

@Repository
internal interface InvestigationRepository : PageableRepository<Investigation, UUID> {

    fun findByDisplayNameIgnoreCaseAndProject_Uuid(displayName: String, projectUUID: UUID) : Optional<Investigation>

}