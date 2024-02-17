package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.project.Project
import java.util.UUID

@Repository
internal interface ProjectRepository : PageableRepository<Project, UUID>