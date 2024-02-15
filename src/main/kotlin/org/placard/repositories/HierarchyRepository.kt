package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.hierarchy.Hierarchy

@Repository
internal interface HierarchyRepository : PageableRepository<Hierarchy, String>