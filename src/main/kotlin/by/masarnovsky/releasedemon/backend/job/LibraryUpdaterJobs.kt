package by.masarnovsky.releasedemon.backend.job

import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.external.service.LastFmLibraryRetriever
import by.masarnovsky.releasedemon.backend.service.ArtistService
import by.masarnovsky.releasedemon.backend.service.UserService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class LibraryUpdaterJobs(
    val lastFmLibraryRetriever: LastFmLibraryRetriever,
    val userService: UserService,
    val artistService: ArtistService,
) {

    fun updateLibraryFromLastfm() {
        logger.info { "updateLibraryFromLastfm started" }

        val processedUsers = userService
            .findUsersWithLastfmProfiles()
            .map { user -> mergeUserArtists(user, lastFmLibraryRetriever.retrieve(user.lastfmUsername!!)) }
            .map { user -> userService.save(user) }
            .count()

        logger.info { "updateLibraryFromLastfm ended, processed $processedUsers users" }
    }

    private fun mergeUserArtists(user: User, artists: List<String>): User {
        logger.info { "user artists: ${user.artists.size}, retrieved from library: ${artists.size}" }

        val actualUserArtists = artistService.findAllByNameIn(artists)

        val newArtists = actualUserArtists.minus(user.artists)
        logger.info { "new artists for user: ${user.id}: $newArtists" }
        // send to telegram

        user.artists.addAll(newArtists)
        return user
    }
}