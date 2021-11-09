package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val repository: UserRepository,
) {
    fun findByLogin(login: String): User? {
        return repository.findByLogin(login)
    }

    fun findByLastFmUsername(username: String): User? {
        return repository.findByLastfmUsername(username)
    }

    fun findAllTelegramUsers(): List<User> {
        return repository.findAllByTelegramIdNotNull()
    }

    fun save(user: User): User {
        return repository.save(user)
    }
}