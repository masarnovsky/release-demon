package by.masarnovsky.releasedemon.backend.job

import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.external.service.LastFmLibraryRetriever
import by.masarnovsky.releasedemon.backend.service.ArtistService
import by.masarnovsky.releasedemon.backend.service.UserService
import by.masarnovsky.releasedemon.backend.utils.getRandomNValues
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}
const val RANDOM_ARTISTS_SUGGESTION_COUNT = 5

@Service
class LibraryUpdaterJobs(
    val lastFmLibraryRetriever: LastFmLibraryRetriever,
    val userService: UserService,
    val artistService: ArtistService,
) {


    fun updateLibraryFromLastfm(chatId: Long) {
        logger.info { "updateLibraryFromLastfm for $chatId started" }

        userService.findByTelegramId(chatId)?.let {
            user -> updateUserLibraryFromLastfm(user)
        }
    }

    fun updateLibraryFromLastfm() {
        logger.info { "updateLibraryFromLastfm for all users started" }

        val processedUsers = userService
            .findUsersWithLastfmProfiles()
            .map { user -> updateUserLibraryFromLastfm(user) }
            .count()

        logger.info { "updateLibraryFromLastfm ended, processed $processedUsers users" }
    }

    @Transactional
    fun updateUserLibraryFromLastfm(user: User) {
        mergeUserArtists(user, lastFmLibraryRetriever.retrieve(user.lastfmUsername!!))
        userService.save(user)
    }

    fun suggestRandomArtists(chatId: Long): Set<Artist> {
        return userService.findByTelegramId(chatId)?.artists?.getRandomNValues(RANDOM_ARTISTS_SUGGESTION_COUNT) ?: setOf()
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