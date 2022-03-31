package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val repository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun findByLastFmUsername(username: String): User? {
        return repository.findByLastfmUsername(username)
    }

    @Transactional(readOnly = true)
    fun findUsersWithLastfmProfiles(): List<User> {
        return repository.findAllByLastfmUsernameNotNull()
    }

    @Transactional(readOnly = true)
    fun findByTelegramId(telegramId: Long): User? {
        return repository.findByTelegramId(telegramId)
    }

    @Transactional
    fun save(user: User): User {
        return repository.save(user)
    }

    @Transactional
    fun clearLastfmUsernameForChatId(chatId: Long) {
        return repository.clearLastfmUsernameForChatId(chatId)
    }
}