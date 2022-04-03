package by.masarnovsky.releasedemon.backend.job

import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.entity.User
import by.masarnovsky.releasedemon.backend.event.NewArtistsEvent
import by.masarnovsky.releasedemon.backend.external.service.LastFmLibraryRetriever
import by.masarnovsky.releasedemon.backend.service.ArtistService
import by.masarnovsky.releasedemon.backend.service.UserService
import by.masarnovsky.releasedemon.backend.utils.getRandomNValues
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}
const val RANDOM_ARTISTS_SUGGESTION_COUNT = 5

@Service
class LibraryUpdaterJobs(
    val lastFmLibraryRetriever: LastFmLibraryRetriever,
    val userService: UserService,
    val artistService: ArtistService,
    val eventPublisher: ApplicationEventPublisher,
) {

    fun updateLibraryFromLastfm(chatId: Long) {
        logger.info { "updateLibraryFromLastfm for $chatId started" }

        userService.findByTelegramId(chatId)?.let { user ->
            updateUserLibraryFromLastfm(user)
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

    fun updateUserLibraryFromLastfm(user: User) {
        mergeUserArtists(user, lastFmLibraryRetriever.retrieve(user.lastfmUsername!!))
        userService.save(user)
    }

    @Transactional(readOnly = true)
    fun suggestRandomArtists(chatId: Long): Set<Artist> { // todo: move to artist's service
        return artistService
            .findUserArtistsByTelegramId(chatId)
            .getRandomNValues(RANDOM_ARTISTS_SUGGESTION_COUNT)
    }

    private fun mergeUserArtists(user: User, actualUserArtists: List<String>): User {
        logger.info { "user artists in db: ${user.artists.size}, retrieved from remote library: ${actualUserArtists.size}" }

        val existedUserArtists = artistService.findAllByNameIn(actualUserArtists).toMutableList()

        if (existedUserArtists.size < actualUserArtists.size) {
            existedUserArtists.addAll(saveNonExistingArtistsFromUserLibrary(existedUserArtists, actualUserArtists))
        }

        val newUserArtists = existedUserArtists.minus(user.artists)
        logger.info { "new artists for user: ${user.id}: $newUserArtists" }

        user.artists.addAll(newUserArtists)
        return user
    }

    private fun saveNonExistingArtistsFromUserLibrary(
        existedUserArtists: MutableList<Artist>,
        actualUserArtists: List<String>
    ): List<Artist> {
        val artistNames = existedUserArtists.map { artist -> artist.name }
        val newArtists = actualUserArtists
            .filter { artistName -> !artistNames.contains(artistName) }
            .map { Artist(name = it) }

        val newArtistsWithoutAlbums = artistService.saveAll(newArtists)
        eventPublisher.publishEvent(NewArtistsEvent(newArtistsWithoutAlbums.map { it.id!! }))

        logger.info { "New artists for common library: $newArtists" }
        return newArtists
    }
}