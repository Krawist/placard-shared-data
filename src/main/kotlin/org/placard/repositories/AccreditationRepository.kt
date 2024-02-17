package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.access.Accreditation
import org.placard.models.user.AbstractUser
import java.util.UUID

@Repository
internal interface AccreditationRepository : PageableRepository<Accreditation, UUID> {

    fun findByAbstractUser(abstractUser: AbstractUser) : List<Accreditation>

    fun findByAbstractUserInList(abstractUsers: List<AbstractUser>) : List<Accreditation>

    fun deleteByAbstractUser(abstractUser: AbstractUser)
}