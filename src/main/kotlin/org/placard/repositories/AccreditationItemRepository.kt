package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.access.Accreditation
import org.placard.models.access.AccreditationItem
import java.util.UUID

@Repository
internal interface AccreditationItemRepository : PageableRepository<AccreditationItem, UUID>{
    fun findByAccreditationInList(accreditations : List<Accreditation>) : List<AccreditationItem>
}