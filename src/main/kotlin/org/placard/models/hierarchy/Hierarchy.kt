package org.placard.models.hierarchy

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Introspected
@Serdeable
@Entity(name = "hierarchies")
internal data class Hierarchy(

    @Id
    val identifier: String,

    @Column(name = "hierarchy_name")
    val name : String = "",
)