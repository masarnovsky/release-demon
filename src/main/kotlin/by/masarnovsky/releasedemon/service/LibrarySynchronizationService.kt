package by.masarnovsky.releasedemon.service

import by.masarnovsky.releasedemon.external.service.LastFmLibraryRetriever
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class LibrarySynchronizationService(
    val artistService: ArtistService,
    val userService: UserService,
) {

    fun synchronizeLibrary(retriever: LastFmLibraryRetriever, username: String) {
        logger.info { "synchronizeLibrary for $username" }

        userService.findByLastFmUsername(username)?.let { user ->
            retriever.retrieve(username).let { artists ->
                user.artists.addAll(artistService.findAllByNameIn(artists))
                userService.save(user)
            }
        }
    }
}