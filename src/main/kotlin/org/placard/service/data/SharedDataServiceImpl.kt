package org.placard.service.data

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.access.AccreditationItemTypeOfAccess
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

        require(userRepository.existsById(dataToUpload.uploadBy)) {
            "Provided creator don't exist"
        }

        require(projectRepository.existsById(dataToUpload.projectUuid) ){
            "Provided project don't exist"
        }

        val shareData = SharableData(
            uuid = UUID.randomUUID(),
            fileSystemPath = "To be proceed",
            displayName = dataToUpload.displayName,
            accessMode = dataToUpload.accessMode,
            projectUuid = dataToUpload.projectUuid
        ).also {
            it.createdBy = dataToUpload.uploadBy
        }

        sharableDataRepository.save(shareData)

        return HttpResponse.created(shareData)
    }

    override fun search(searchQueryDescription: SearchQueryDescription): HttpResponse<List<SharableData>> {
        val requesterAccreditations = getUserAccreditations(UUID.fromString(searchQueryDescription.requesterUuid))
        //val accreditationItems = getAccreditationsHierarchyItems(requesterAccreditations)
        //val accreditationsLabels = accreditationItems.map { it.level }.distinct()

        return HttpResponse.ok(sharableDataRepository.findAll())

/*        return HttpResponse.ok(
            sharableDataRepository.getSharableDataByUserAccreditationLevelsAndItems(
                accreditationsLevels = accreditationsLabels,
                accreditationItems = accreditationItems
            )
        )*/
    }

    private fun getUserAccreditations(userUUID: UUID): List<Accreditation> {
        //TODO find a way to this with one database request
        val user = userRepository.findById(userUUID).orElseThrow {
            IllegalArgumentException("User with uuid $userUUID not found")
        }
        val userGroups = userGroupMemberShipRepository.findByUserGroupMemberShipKeyUserUuidAndStatus(
            userUuid = userUUID,
            status = UserGroupMemberShipStatus.ACTIVE
        ).mapNotNull {
            userGroupRepository.findById(it.userGroupMemberShipKey.userGroupUuid).orElse(null)
        }

        return accreditationRepository.findByAbstractUserUuidInList(userGroups.toMutableList().plus(user).map { it.uuid })
    }

    private fun getAccreditationsHierarchyItems(accreditations: List<UUID>): List<UUID> {
        val hierarchyItems = mutableListOf<UUID>()
        accreditationItemRepository.findByAccreditationUuidInList(accreditations).forEach {
            if(it.typeOfAccess == AccreditationItemTypeOfAccess.ALL_DESCENDANT_OF_LEVEL){
                hierarchyItems.addAll(getHierarchyItemChild(it.hierarchyItemUuid))
            } else {
                hierarchyItems.add(it.hierarchyItemUuid)
            }
        }
        return hierarchyItems
    }

    private fun getHierarchyItemChild(hierarchyItem: UUID): List<UUID> {
        val children = mutableListOf<UUID>()
        val waves = hierarchyItemRepository.findByParentUuid(hierarchyItem)
        if (waves.isNotEmpty()) {
            children.addAll(waves.map { getHierarchyItemChild(it.uuid) }.flatten().distinct())
        }
        children.add(hierarchyItem)
        return children
    }
}