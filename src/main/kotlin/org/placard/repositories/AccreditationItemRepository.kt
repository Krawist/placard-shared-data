package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.access.Accreditation
import org.placard.models.access.AccreditationItem

@Repository
internal interface AccreditationItemRepository : PageableRepository<AccreditationItem, String>{
    fun findByAccreditationInList(accreditations : List<Accreditation>) : List<AccreditationItem>
}