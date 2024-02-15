package org.placard.repositories

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.project.Project

@Repository
internal interface ProjectRepository : PageableRepository<Project, String>