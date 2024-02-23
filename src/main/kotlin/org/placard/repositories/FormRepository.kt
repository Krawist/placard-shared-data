package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.form.Form
import java.util.Optional
import java.util.UUID

@MongoRepository
internal interface FormRepository : PageableRepository<Form, UUID> {

    fun findByDisplayNameIgnoreCaseAndInvestigationUuid(displayName : String, investigationUuid: UUID) : Optional<Form>
    fun findByDisplayNameIgnoreCaseAndInvestigationStepUuid(displayName : String, investigationStepUuid: UUID) : Optional<Form>

}