package by.masarnovsky.releasedemon.bot

import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.types.Message
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Service
class ReleaseDemonBot {

    @Value("\${BOT_USERNAME}")
    private lateinit var username: String
    @Value("\${BOT_TOKEN}")
    private lateinit var token: String
    @Value("\${OWNER_ID}")
    private lateinit var ownerId: String
    var isProd = false
    private lateinit var bot: Bot

    private val botService: BotService

    @Autowired
    constructor(botService: BotService) {
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
//            val properties = Properties()
//            val propertiesFile = System.getProperty("user.dir") + "\\telegram.properties"
//            val inputStream = FileInputStream(propertiesFile)
//            properties.load(inputStream)
//            token = properties.getProperty("BOT_TOKEN")
//            username = properties.getProperty("BOT_USERNAME")
//            ownerId = properties.getProperty("OWNER_ID")
        }
    }

    @PostConstruct
    private fun startBot() {
        logger.info { "Starting ReleaseDemonBot bot" }
//        loadProperties()
        bot = Bot.createPolling(username, token)
        setBehaviour()
        bot.start()
    }

    private fun setBehaviour() {
        startCommand()
        suggestCommand()
        onMessage()
    }

    private fun startCommand() {
        bot.chain(START_COMMAND) { message ->
            logger.info { "$START_COMMAND command was called" }

            val (chatId, _) = getChatIdAndTextFromMessage(message)
            botService.saveUser(message)
            sendMessage(chatId, "Please, send your lastfm login and i will try to save your library")
        }
            .then { message -> val (chatId, text) = getChatIdAndTextFromMessage(message)
            botService.saveLastFmUsername(chatId, text)
                sendMessage(chatId, "<b>$text</b> saved as lastfm username")
            }
            .build()

    }

    private fun suggestCommand() {
        bot.onCommand(SUGGEST_COMMAND) { message, _ ->
            var (chatId, _) = getChatIdAndTextFromMessage(message)
            sendMessage(chatId, botService.suggestArtists(chatId).toString())
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

    fun sendMessage(text: String) {
        sendMessage(ownerId, text)
    }

    private fun getChatIdAndTextFromMessage(message: Message): ChatIdAndText {
        return ChatIdAndText(message.chat.id, message.text ?: "")
    }

    private data class ChatIdAndText(val chatId: Long, val text: String)
}