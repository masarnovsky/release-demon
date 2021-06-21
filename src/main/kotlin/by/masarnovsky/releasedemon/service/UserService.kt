package by.masarnovsky.releasedemon.service

import by.masarnovsky.releasedemon.entity.User
import by.masarnovsky.releasedemon.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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