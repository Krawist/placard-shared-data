package org.placard.models.hierarchy

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.placard.models.Auditable
import java.util.UUID

@Introspected
@Serdeable
@Entity(name = "hierarchies")
internal data class Hierarchy(

    @Id
    val uuid: UUID,

    @Column(name = "hierarchy_name")
    val displayName : String = "",
) : Auditable()