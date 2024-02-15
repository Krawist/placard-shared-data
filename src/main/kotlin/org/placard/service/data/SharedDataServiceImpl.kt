package org.placard.service.data

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.config.AccreditationItemTypeOfAccess
import org.placard.models.access.Accreditation
import org.placard.models.data.SharableData
import org.placard.models.hierarchy.HierarchyItem
import org.placard.models.user.UserGroupMemberShipStatus
import org.placard.remote.DataToUpload
import org.placard.remote.SearchQueryDescription
import org.placard.repositories.AccreditationItemRepository
import org.placard.repositories.AccreditationRepository
import org.placard.repositories.HierarchyItemRepository
import org.placard.repositories.ProjectRepository
import org.placard.repositories.SharableDataRepository
import org.placard.repositories.UserGroupMemberShipRepository
import org.placard.repositories.UserGroupRepository
import org.placard.repositories.UserRepository
import java.util.UUID

@Singleton
internal class SharedDataServiceImpl(
    private val sharableDataRepository: SharableDataRepository,
    private val userGroupRepository: UserGroupRepository,
    private val userRepository: UserRepository, //TODO use service here
    private val projectRepository: ProjectRepository, //TODO use service here
    private val accreditationRepository: AccreditationRepository,
    private val userGroupMemberShipRepository: UserGroupMemberShipRepository,
    private val accreditationItemRepository: AccreditationItemRepository,
    private val hierarchyItemRepository: HierarchyItemRepository,
) : SharedDataService {

    override fun uploadData(dataToUpload: DataToUpload): HttpResponse<SharableData> {

        require(dataToUpload.displayName.isNotBlank()) { "display name cannot be blank" }
        require(dataToUpload.uploadBy.isNotBlank()) { "Please provide the uploader ID" }
        require(dataToUpload.projectIdentifier.isNotBlank()) { "Please provide the project of this data" }

        val creator = userRepository.findById(dataToUpload.uploadBy).orElseThrow {
            IllegalArgumentException("Provided creator don't exist")
        }

        val project = projectRepository.findById(dataToUpload.projectIdentifier).orElseThrow {
            IllegalArgumentException("Provided project don't exist")
        }

        val shareData = SharableData(
            uploadedBy = creator,
            fileSystemPath = "To be proceed",
            displayName = dataToUpload.displayName,
            accessMode = dataToUpload.accessMode,
            project = project
        ).also {
            it.identifier = UUID.randomUUID().toString()
        }

        sharableDataRepository.save(shareData)

        return HttpResponse.created(shareData)
    }

    override fun search(searchQueryDescription: SearchQueryDescription): HttpResponse<List<SharableData>> {
        val requesterAccreditations = getUserAccreditations(UUID.fromString(searchQueryDescription.requesterUuid))
        val accreditationItems = getAccreditationsHierarchyItems(requesterAccreditations)
        val accreditationsLabels = accreditationItems.map { it.level }.distinct()

        return HttpResponse.ok(
            sharableDataRepository.getSharableDataByUserAccreditationLevelsAndItems(
                accreditationsLevels = accreditationsLabels,
                accreditationItems = accreditationItems
            )
        )
    }

    private fun getUserAccreditations(userUUID: UUID): List<Accreditation> {
        //TODO find a way to this with one database request
        val user = userRepository.findById(userUUID.toString()).orElseThrow {
            IllegalArgumentException("User with uuid $userUUID not found")
        }
        val userGroups = userGroupMemberShipRepository.findByUserGroupMemberShipKeyUserUuidAndStatus(
            userUuid = userUUID,
            status = UserGroupMemberShipStatus.ACTIVE
        ).mapNotNull {
            userGroupRepository.findById(it.userGroupMemberShipKey.userGroupUuid.toString()).orElse(null)
        }

        return accreditationRepository.findByAbstractUserInList(userGroups.toMutableList().plus(user))
    }

    private fun getAccreditationsHierarchyItems(accreditations: List<Accreditation>): List<HierarchyItem> {
        val hierarchyItems = mutableListOf<HierarchyItem>()
        accreditationItemRepository.findByAccreditationInList(accreditations).forEach {
            if(it.typeOfAccess == AccreditationItemTypeOfAccess.ALL_DESCENDANT_OF_LEVEL){
                hierarchyItems.addAll(getHierarchyItemChild(it.hierarchyItem))
            } else {
                hierarchyItems.add(it.hierarchyItem)
            }
        }
        return hierarchyItems
    }

    private fun getHierarchyItemChild(hierarchyItem: HierarchyItem): List<HierarchyItem> {
        val children = mutableListOf<HierarchyItem>()
        val waves = hierarchyItemRepository.findByParent(hierarchyItem)
        if (waves.isNotEmpty()) {
            children.addAll(waves.map { getHierarchyItemChild(it) }.flatten().distinct())
        }
        children.add(hierarchyItem)
        return children
    }
}