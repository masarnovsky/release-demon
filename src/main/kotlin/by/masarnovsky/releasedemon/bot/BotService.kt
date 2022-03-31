package by.masarnovsky.releasedemon.bot

import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.event.LastfmUsernameSavedEvent
import by.masarnovsky.releasedemon.backend.job.LibraryUpdaterJobs
import by.masarnovsky.releasedemon.backend.service.UserService
import com.elbekD.bot.types.Message
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class BotService(
    val userService: UserService,
    val jobs: LibraryUpdaterJobs,
    val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun saveLastFmUsername(chatId: Long, lastfmUsername: String) {
        val user = userService.findByTelegramId(chatId)
        if (user != null && user.lastfmUsername != lastfmUsername) {
            user.lastfmUsername = lastfmUsername

            logger.info { "populate user with lastfm username $user" }
            userService.save(user)
            eventPublisher.publishEvent(LastfmUsernameSavedEvent(user.telegramId!!))
        } else if (user != null) {
            logger.info { "lastfm username for $chatId already exists" }
        } else {
            logger.info { "user not found" }
        }
    }

    fun clearLastfmUsernameForChatId(chatId: Long) {
        logger.info { "Clear lastfm username for chatId=$chatId" }
        userService.clearLastfmUsernameForChatId(chatId)
    }

    fun retrieveTextFromCommand(command: String, pattern: String): String {
        return command.replace(Regex("$pattern ?"), "")
    }

    fun saveUser(message: Message) {
        val dbUser = userService.findByTelegramId(message.chat.id)

        if (dbUser == null) {
            val user = User.fromMessage(message)
            logger.info { "save new user $user" }
            userService.save(user)
        }
    }

    fun suggestArtists(chatId: Long): List<String> {
        return jobs.suggestRandomArtists(chatId).map { artist -> artist.name }
    }
}