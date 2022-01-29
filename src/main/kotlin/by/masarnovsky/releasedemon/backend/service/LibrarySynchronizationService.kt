package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.external.service.LastFmLibraryRetriever
import by.masarnovsky.releasedemon.backend.external.service.SpotifyArtistsRetriever
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
@Deprecated("will be moved to jobs")
class LibrarySynchronizationService(
    val artistService: ArtistService,
    val userService: UserService,
    val lastFmLibraryRetriever: LastFmLibraryRetriever,
    val spotifyRetriever: SpotifyArtistsRetriever,
) {

    fun synchronizeLibraryFromLastFm(username: String) { // todo: delete as duplicate of LibraryUpdaterJobs
        logger.info { "synchronizeLibraryFromLastFm for $username" }

        userService.findByLastFmUsername(username)?.let { user ->
            addArtistsToUserAndSave(user, lastFmLibraryRetriever.retrieve(username))
        }
    }

    private fun addArtistsToUserAndSave(user: User, artists:List<String>) {
        logger.info { "user artists: ${user.artists.size}, retrieved from library: ${artists.size}" }

        user.artists.addAll(artistService.findAllByNameIn(artists))
        userService.save(user)
    }
}