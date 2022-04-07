package by.masarnovsky.releasedemon.bot

import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.event.LastfmUsernameSavedEvent
import by.masarnovsky.releasedemon.backend.service.ArtistService
import by.masarnovsky.releasedemon.backend.service.UserService
import com.elbekD.bot.types.Message
import mu.KotlinLogging
import org.springframework.boot.info.BuildProperties
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class BotService(
    val userService: UserService,
    val artistService: ArtistService,
    val eventPublisher: ApplicationEventPublisher,
    val buildProperties: BuildProperties,
) {

  @Transactional
  fun saveLastFmUsername(chatId: Long, lastfmUsername: String) {
    val user = userService.findByTelegramId(chatId)
    if (user != null && user.lastfmUsername != lastfmUsername) {
      user.lastfmUsername = lastfmUsername

      logger.info { "populate user with lastfm username $lastfmUsername" }
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
    logger.info { "Artists suggestion for $chatId" }
    return artistService.suggestRandomArtists(chatId).map { artist -> artist.name }
  }

  fun getUserInfo(chatId: Long): String {
    logger.info { "Retrieve user info for $chatId" }
    return (userService.findByTelegramId(chatId)?.toString()
        ?: "none") + "\nversion: ${buildProperties.version}"
  }

  fun updateUserLibrary(chatId: Long): String {
    logger.info { "Update user library for $chatId" }
    eventPublisher.publishEvent(LastfmUsernameSavedEvent(chatId))
    return "We started updating your library"
  }
}
