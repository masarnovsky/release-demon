package by.masarnovsky.releasedemon.bot

import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.types.Message
import javax.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ReleaseDemonBot
@Autowired
constructor(
    private val botService: BotService,
    @Value("\${bot.username}") private var username: String,
    @Value("\${bot.token}") private var token: String,
) {

  private lateinit var bot: Bot

  @PostConstruct
  private fun startBot() {
    logger.info { "Starting ReleaseDemonBot bot" }
    bot = Bot.createPolling(username, token)
    setBehaviour()
    bot.start()
  }

  private fun setBehaviour() {
    startCommand()
    meCommand()
    updateCommand()
    suggestCommand()
    clearCommand()
    onMessage()
  }

  private fun startCommand() {
    bot
        .chain(START_COMMAND) { message ->
          logger.info { "$START_COMMAND command was called" }

          val (chatId, _) = getChatIdAndTextFromMessage(message)
          botService.saveUser(message)
          sendMessage(chatId, "Please, send your lastfm login and i will try to save your library")
        }
        .then { message ->
          val (chatId, text) = getChatIdAndTextFromMessage(message)
          botService.saveLastFmUsername(chatId, text)
          sendMessage(
              chatId,
              "<b>$text</b> saved as lastfm username. we started retrieve your library") // todo:
          // change
          // text (in
          // case
          // some
          // error)
        }
        .build()
  }

  private fun suggestCommand() {
    bot.onCommand(SUGGEST_COMMAND) { message, _ ->
      val (chatId, _) = getChatIdAndTextFromMessage(message)
      sendMessage(chatId, botService.suggestArtists(chatId).toString())
    }
  }

  private fun meCommand() {
    bot.onCommand(ME_COMMAND) { message, _ ->
      val (chatId, _) = getChatIdAndTextFromMessage(message)
      sendMessage(chatId, botService.getUserInfo(chatId))
    }
  }

  private fun updateCommand() {
    bot.onCommand(UPDATE_COMMAND) { message, _ ->
      val (chatId, _) = getChatIdAndTextFromMessage(message)
      sendMessage(chatId, botService.updateUserLibrary(chatId))
    }
  }

  private fun clearCommand() {
    bot.onCommand(CLEAR_COMMAND) { message, _ ->
      val (chatId, _) = getChatIdAndTextFromMessage(message)
      botService.clearLastfmUsernameForChatId(chatId)
    }
  }

  private fun onMessage() {
    bot.onMessage { message ->
      val (chatId, _) = getChatIdAndTextFromMessage(message)

      sendMessage(chatId, "I can't understand your command")
    }
  }

  fun sendMessage(chatId: Any, text: String) {
    bot.sendMessage(chatId = chatId, text = text, parseMode = "HTML")
  }

  private fun getChatIdAndTextFromMessage(message: Message): ChatIdAndText {
    return ChatIdAndText(message.chat.id, message.text ?: "")
  }

  private data class ChatIdAndText(val chatId: Long, val text: String)
}
