package org.placard.repositories

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import org.placard.models.project.Project
import java.util.UUID

@MongoRepository
internal interface ProjectRepository : PageableRepository<Project, UUID>