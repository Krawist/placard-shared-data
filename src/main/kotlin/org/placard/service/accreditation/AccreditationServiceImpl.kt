package org.placard.service.accreditation

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.access.Accreditation
import org.placard.models.access.AccreditationItem
import org.placard.models.access.AccreditationStatus
import org.placard.remote.AccreditationCreationRequest
import org.placard.remote.AccreditationItemCreationRequest
import org.placard.repositories.AccreditationItemRepository
import org.placard.repositories.AccreditationRepository
import org.placard.repositories.HierarchyItemRepository
import org.placard.repositories.ProjectRepository
import org.placard.repositories.SharableDataRepository
import java.util.UUID

@Singleton
internal class AccreditationServiceImpl(
    private val accreditationRepository: AccreditationRepository,
    private val accreditationItemRepository: AccreditationItemRepository,
    private val projectRepository: ProjectRepository,
    private val hierarchyItemRepository: HierarchyItemRepository,
    private val sharableDataRepository: SharableDataRepository
) : AccreditationService {
    override fun addAccreditationsToUser(abstractUserUuid : UUID, accreditations: List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>>{

        //We save the new ones
        val createdAccreditations = accreditations.map { accreditationCreationRequest ->

            checkAccreditationItems(accreditationCreationRequest = accreditationCreationRequest, accreditationItemsCreationRequest = accreditationCreationRequest.items)

/*            require(abstractUserRepository.existsById(abstractUserUuid)){
                "Provide user/user group with id '$abstractUserUuid' don't exist"
            }*/

            require(projectRepository.existsById(accreditationCreationRequest.projectUuid)){
                "Project with id '${accreditationCreationRequest.projectUuid}' not found"
            }

            val accreditation = Accreditation(
                uuid = UUID.randomUUID(),
                level = accreditationCreationRequest.level,
                projectUuid = accreditationCreationRequest.projectUuid,
                abstractUserUuid = abstractUserUuid,
                accreditationStatus = AccreditationStatus.ACTIVE,
            )


            accreditationRepository.save(accreditation)

            saveAccreditationItems(
                accreditation = accreditation,
                accreditationItemsCreationRequest = accreditationCreationRequest.items
            )

            accreditationRepository.findById(accreditation.uuid).get()
        }

        return HttpResponse.created(createdAccreditations)
    }

    override fun addAccreditationsToData(sharableDataUuid : UUID, accreditations: List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>> {

        //We save the new ones
        val createdAccreditations = accreditations.map { accreditationCreationRequest ->

            checkAccreditationItems(accreditationCreationRequest = accreditationCreationRequest, accreditationItemsCreationRequest = accreditationCreationRequest.items)

            require(sharableDataRepository.existsById(sharableDataUuid)){
                "Provide sharable data with id '$sharableDataUuid' not found"
            }

            val accreditation = Accreditation(
                uuid = UUID.randomUUID(),
                level = accreditationCreationRequest.level,
                sharableDataUuid = sharableDataUuid,
                accreditationStatus = AccreditationStatus.ACTIVE
            )
            accreditationRepository.save(accreditation)

            saveAccreditationItems(
                accreditation = accreditation,
                accreditationItemsCreationRequest = accreditationCreationRequest.items
            )

            accreditationRepository.findById(accreditation.uuid).get()
        }

        return HttpResponse.created(createdAccreditations)
    }

    override fun removeAccreditations(accreditations: List<UUID>) : HttpResponse<Nothing> {
        accreditations.forEach {
            accreditationRepository.deleteById(it)
        }
        return HttpResponse.ok()
    }

    private fun saveAccreditationItems(
        accreditation: Accreditation,
        accreditationItemsCreationRequest: List<AccreditationItemCreationRequest>,
    ) {
        val accreditationItems = accreditationItemsCreationRequest.map { accreditationItemCreationRequest ->
            val hierarchyItem = hierarchyItemRepository.findById(accreditationItemCreationRequest.accreditedItem).get()
            AccreditationItem(
                uuid = UUID.randomUUID(),
                hierarchyItemUuid = hierarchyItem.uuid,
                accreditationUuid = accreditation.uuid,
                typeOfAccess = accreditationItemCreationRequest.typeOfAccess
            )
        }
        accreditationItemRepository.saveAll(accreditationItems)
    }

    private fun checkAccreditationItems(accreditationCreationRequest: AccreditationCreationRequest, accreditationItemsCreationRequest: List<AccreditationItemCreationRequest>){

        require(accreditationItemsCreationRequest.isNotEmpty()){IllegalArgumentException("The list of authorization items cannot be empty")}

        accreditationItemsCreationRequest.forEach {
            hierarchyItemRepository.findById(it.accreditedItem).orElseThrow {
                IllegalArgumentException("Hierarchy item with id '${it.accreditedItem}' not found")
            }.let { hierarchyItem ->
                require(hierarchyItem.level != accreditationCreationRequest.level) {
                    "Hierarchy item ${hierarchyItem.uuid} is not of level ${accreditationCreationRequest.level}"
                }
            }
        }
    }
}