package org.placard.models

import io.micronaut.data.annotation.DateCreated
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.ManyToOne
import org.placard.models.user.User
import java.time.Instant
import java.util.UUID

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
internal abstract class Auditable(
    @Id
    var uuid: UUID? = null,

    @DateCreated
    var createdAt: Instant? = null,

    @DateCreated
    var lastUpdateAt: Instant? = null,

    @ManyToOne
    var lastUpdatedBy: User? = null,

    @ManyToOne
    var createdBy: User? = null,
)