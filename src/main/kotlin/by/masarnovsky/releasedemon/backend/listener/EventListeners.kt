package by.masarnovsky.releasedemon.backend.listener

import by.masarnovsky.releasedemon.backend.event.LastfmUsernameSavedEvent
import by.masarnovsky.releasedemon.backend.event.NewArtistsEvent
import by.masarnovsky.releasedemon.backend.job.LibraryUpdaterJobs
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class EventListeners(
    val libraryUpdaterJobs: LibraryUpdaterJobs,
) {

    @EventListener
    fun handleNewLastfmUsernameEvent(event: LastfmUsernameSavedEvent) {
        logger.info { "handleNewLastfmUsernameEvent=$event" }
        libraryUpdaterJobs.updateLibraryFromLastfm(event.chatId)
        logger.info { "handleNewLastfmUsernameEvent finished" }
    }

    @EventListener
    fun handleNewArtistsEvent(event: NewArtistsEvent) {
        logger.info { "handleNewArtistsEvent=$event" }
    }
}