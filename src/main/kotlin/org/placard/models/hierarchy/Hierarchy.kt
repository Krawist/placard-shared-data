package org.placard.models.hierarchy

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.placard.models.Auditable

@Introspected
@Serdeable
@Entity(name = "hierarchies")
internal data class Hierarchy(
    @Column(name = "hierarchy_name")
    val displayName : String = "",
) : Auditable()