package org.placard.models

import io.micronaut.data.annotation.DateCreated
import jakarta.persistence.Column
import java.time.Instant
import java.util.UUID

internal abstract class Auditable(
    @DateCreated
    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @DateCreated
    @Column(name = "last_updated_at")
    var lastUpdatedAt: Instant? = null,

    @Column(name = "last_updated_by")
    var lastUpdatedBy: UUID? = null,

    @Column(name = "created_by")
    var createdBy: UUID? = null,
)