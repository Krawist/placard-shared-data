package org.placard.service.user

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import org.placard.models.user.User
import org.placard.remote.UserCreationRequest
import org.placard.repositories.UserRepository
import java.util.UUID

@Singleton
internal class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun create(userCreationRequest: UserCreationRequest): HttpResponse<User> {
        //TODO make this method optimal by handling unity of email address
        val user = User(
            firstName = userCreationRequest.firstName,
            lastName = userCreationRequest.lastName,
            emailAddress = userCreationRequest.emailAddress
        ).also {
            it.identifier = UUID.randomUUID().toString()
        }

        userRepository.save(user)

        return HttpResponse.created(user)
    }

    override fun search(): HttpResponse<Page<User>> {
        return HttpResponse.ok(userRepository.findAll(Pageable.unpaged()))
    }
}