package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.access.Accreditation
import org.placard.models.access.AccreditationItem
import java.util.UUID

@MongoRepository
internal interface AccreditationItemRepository : PageableRepository<AccreditationItem, UUID>{
    fun findByAccreditationUuidInList(accreditations : List<UUID>) : List<AccreditationItem>
}