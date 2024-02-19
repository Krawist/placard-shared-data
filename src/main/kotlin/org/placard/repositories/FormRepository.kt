package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.form.Form
import java.util.Optional
import java.util.UUID

@MongoRepository
internal interface FormRepository : PageableRepository<Form, UUID> {

    fun findByDisplayNameIgnoreCaseAndProject_Uuid(displayName : String, projectUUID: UUID) : Optional<Form>
    fun findByDisplayNameIgnoreCaseAndInvestigationStep_Uuid(displayName : String, investigationStepUuid: UUID) : Optional<Form>

}