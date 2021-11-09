package by.masarnovsky.releasedemon.bot

import by.masarnovsky.releasedemon.service.UserService
import com.elbekD.bot.Bot
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

    @Autowired
    constructor(userService: UserService) {
        this.userService = userService
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
        onCommand()
        onMessage()
    }

    private fun onCommand() {
        bot.onCommand(START_COMMAND) { _, _ ->
            logger.info { "/start command was called" }
            sendMessage("TODO")
        }
    }

    private fun onMessage() {
        bot.onMessage { message ->

            var (chatId, text) = getChatIdAndTextFromMessage(message)

            sendMessage(chatId, text)
        }
    }

    fun sendMessage(chatId: Any, text: String) {
        bot.sendMessage(chatId = chatId, text = text, parseMode = "Markdown")
    }

    fun sendMessage(text: String) {
        sendMessage(ownerId, text)
    }

    private fun getChatIdAndTextFromMessage(message: Message): ChatIdAndText {
        return ChatIdAndText(message.chat.id, message.text ?: "")
    }

    private data class ChatIdAndText(val chatId: Long, val text: String)

}