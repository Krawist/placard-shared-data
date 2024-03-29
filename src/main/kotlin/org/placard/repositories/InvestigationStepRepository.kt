package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.investigation.InvestigationStep
import java.util.Optional
import java.util.UUID

@MongoRepository
internal interface InvestigationStepRepository : PageableRepository<InvestigationStep, UUID> {

    fun findByDisplayNameIgnoreCaseAndInvestigationUuid(displayName : String, investigationUuid: UUID) : Optional<InvestigationStep>

}