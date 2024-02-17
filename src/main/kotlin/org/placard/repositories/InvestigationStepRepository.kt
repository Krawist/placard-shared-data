package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.investigation.InvestigationStep
import java.util.Optional
import java.util.UUID

@Repository
internal interface InvestigationStepRepository : PageableRepository<InvestigationStep, UUID> {

    fun findByDisplayNameIgnoreCaseAndInvestigation_Uuid(displayName : String, investigationUUID: UUID) : Optional<InvestigationStep>

}