package by.masarnovsky.releasedemon.service

import by.masarnovsky.releasedemon.entity.User
import by.masarnovsky.releasedemon.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val repository: UserRepository,
) {
    fun findByLogin(login: String): User? {
        return repository.findByLogin(login)
    }

    fun findAllTelegramUsers(): List<User> {
        return repository.findAllByTelegramIdNotNull()
    }
}