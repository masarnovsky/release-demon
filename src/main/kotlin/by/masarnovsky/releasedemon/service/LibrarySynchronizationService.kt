package by.masarnovsky.releasedemon.service

import by.masarnovsky.releasedemon.entity.User
import by.masarnovsky.releasedemon.external.service.LastFmLibraryRetriever
import by.masarnovsky.releasedemon.external.service.SpotifyArtistsRetriever
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class LibrarySynchronizationService(
    val artistService: ArtistService,
    val userService: UserService,
    val lastFmLibraryRetriever: LastFmLibraryRetriever,
    val spotifyRetriever: SpotifyArtistsRetriever,
) {

    fun synchronizeLibraryFromLastFm(username: String) {
        logger.info { "synchronizeLibraryFromLastFm for $username" }

        userService.findByLastFmUsername(username)?.let { user ->
            addArtistsToUserAndSave(user, lastFmLibraryRetriever.retrieve(username))
        }
    }

    fun synchronizeLibraryFromSpotify(username: String) {
        logger.info { "synchronizeLibraryFromSpotify" }

        userService.findByLogin(username)?.let {user->
            addArtistsToUserAndSave(user, spotifyRetriever.retrieve(username))
        }
    }

    private fun addArtistsToUserAndSave(user:User, artists:List<String>) {
        user.artists.addAll(artistService.findAllByNameIn(artists))
        userService.save(user)
    }
}