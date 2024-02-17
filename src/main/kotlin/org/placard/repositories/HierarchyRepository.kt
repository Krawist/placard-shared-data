package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.hierarchy.Hierarchy
import java.util.UUID

@Repository
internal interface HierarchyRepository : PageableRepository<Hierarchy, UUID>