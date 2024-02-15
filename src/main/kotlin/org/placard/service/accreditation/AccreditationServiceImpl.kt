package org.placard.service.accreditation

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.access.Accreditation
import org.placard.models.access.AccreditationItem
import org.placard.models.access.AccreditationStatus
import org.placard.remote.AccreditationCreationRequest
import org.placard.remote.AccreditationItemCreationRequest
import org.placard.repositories.AbstractUserRepository
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
    private val abstractUserRepository: AbstractUserRepository,
    private val sharableDataRepository: SharableDataRepository
) : AccreditationService {
    override fun addAccreditationsToUser(abstractUserUuid : String, accreditations: List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>>{

        //We save the new ones
        val createdAccreditations = accreditations.map { accreditationCreationRequest ->

            checkAccreditationItems(accreditationCreationRequest = accreditationCreationRequest, accreditationItemsCreationRequest = accreditationCreationRequest.items)

            val abstractUser = abstractUserRepository.findById(abstractUserUuid).orElseThrow {
                IllegalArgumentException("Provide user/user group with id '$abstractUserUuid' don't exist")
            }
            val project = projectRepository.findById(accreditationCreationRequest.projectIdentifier).orElseThrow {
                IllegalArgumentException("Project with id '${accreditationCreationRequest.projectIdentifier}' not found")
            }

            val accreditation = Accreditation(
                identifier = UUID.randomUUID().toString(),
                level = accreditationCreationRequest.level,
                project = project,
                abstractUser = abstractUser,
                accreditationStatus = AccreditationStatus.ACTIVE,
            )
            accreditationRepository.save(accreditation)

            saveAccreditationItems(
                accreditation = accreditation,
                accreditationItemsCreationRequest = accreditationCreationRequest.items
            )

            accreditationRepository.findById(accreditation.identifier).get()
        }

        return HttpResponse.created(createdAccreditations)
    }

    override fun addAccreditationsToData(sharableDataUuid : String, accreditations: List<AccreditationCreationRequest>) : HttpResponse<List<Accreditation>> {

        //We save the new ones
        val createdAccreditations = accreditations.map { accreditationCreationRequest ->

            checkAccreditationItems(accreditationCreationRequest = accreditationCreationRequest, accreditationItemsCreationRequest = accreditationCreationRequest.items)

            val sharableData = sharableDataRepository.findById(sharableDataUuid).orElseThrow {
                IllegalArgumentException("Provide sharable data with id '$sharableDataUuid' not found")
            }

            val accreditation = Accreditation(
                identifier = UUID.randomUUID().toString(),
                level = accreditationCreationRequest.level,
                sharableData = sharableData,
                accreditationStatus = AccreditationStatus.ACTIVE
            )
            accreditationRepository.save(accreditation)

            saveAccreditationItems(
                accreditation = accreditation,
                accreditationItemsCreationRequest = accreditationCreationRequest.items
            )

            accreditationRepository.findById(accreditation.identifier).get()
        }

        return HttpResponse.created(createdAccreditations)
    }

    override fun removeAccreditations(accreditations: List<String>) : HttpResponse<Nothing> {
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
                identifier = UUID.randomUUID().toString(),
                hierarchyItem = hierarchyItem,
                accreditation = accreditation,
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
                if(hierarchyItem.level != accreditationCreationRequest.level){
                    throw IllegalArgumentException("Hierarchy item ${hierarchyItem.identifier} is not of level ${accreditationCreationRequest.level}")
                }
            }
        }
    }
}