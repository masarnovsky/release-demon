package by.masarnovsky.releasedemon.bot

import by.masarnovsky.releasedemon.backend.external.service.LastFmLibraryRetriever
import by.masarnovsky.releasedemon.backend.service.UserService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.types.Message
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.util.*
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Service
class ReleaseDemonBot {

    private lateinit var username: String
    private lateinit var token: String
    private lateinit var ownerId: String
    var isProd = false
    private lateinit var bot: Bot

    private val userService: UserService
    private val retriever: LastFmLibraryRetriever
    private val botService: BotService

    @Autowired
    constructor(userService: UserService, retriever: LastFmLibraryRetriever, botService: BotService) {
        this.userService = userService
        this.retriever = retriever
        this.botService = botService
    }


    private fun loadProperties() {
        if (System.getenv()["IS_PROD"].toString() != "null") {
            logger.info { "setup prod environment" }
            isProd = true
            token = System.getenv()["BOT_TOKEN"].toString()
            username = System.getenv()["BOT_USERNAME"].toString()
            ownerId = System.getenv()["OWNER_ID"].toString()
        } else {
            logger.info { "setup test environment" }
            val properties = Properties()
            val propertiesFile = System.getProperty("user.dir") + "\\telegram.properties"
            val inputStream = FileInputStream(propertiesFile)
            properties.load(inputStream)
            token = properties.getProperty("BOT_TOKEN")
            username = properties.getProperty("BOT_USERNAME")
            ownerId = properties.getProperty("OWNER_ID")
        }
    }

    @PostConstruct
    private fun startBot() {
        logger.info { "Starting ReleaseDemonBot bot" }
        loadProperties()
        bot = Bot.createPolling(username, token)
        setBehaviour()
        bot.start()
    }

    private fun setBehaviour() {
        startCommand()
        onMessage()
    }

    private fun startCommand() {
        bot.chain(START_COMMAND) { message ->
            logger.info { "$START_COMMAND command was called" }

            var (chatId, text) = getChatIdAndTextFromMessage(message)
            botService.saveUser(message)
            sendMessage(chatId, "Please, send your lastfm login and i will try to save your library")
        }
            .then { message -> var (chatId, text) = getChatIdAndTextFromMessage(message)
            botService.saveLastFmUsername(chatId, text)
                sendMessage(chatId, "<b>$text</b> saved as lastfm username")
            }
            .build()

    }

    private fun lastfmCommand() {
        bot.onCommand(LASTFM_COMMAND) { message, _ ->
            logger.info { "$LASTFM_COMMAND was called" }

            var (chatId, text) = getChatIdAndTextFromMessage(message)

        }
    }

    private fun onMessage() {
        bot.onMessage { message ->

            var (chatId, text) = getChatIdAndTextFromMessage(message)

            sendMessage(chatId, text)
        }
    }

    fun sendMessage(chatId: Any, text: String) {
        bot.sendMessage(chatId = chatId, text = text, parseMode = "HTML")
    }

    fun sendMessage(text: String) {
        sendMessage(ownerId, text)
    }

    private fun getChatIdAndTextFromMessage(message: Message): ChatIdAndText {
        return ChatIdAndText(message.chat.id, message.text ?: "")
    }

    private data class ChatIdAndText(val chatId: Long, val text: String)
}