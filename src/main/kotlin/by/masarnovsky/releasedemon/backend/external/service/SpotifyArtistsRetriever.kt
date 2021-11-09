package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.SpotifyArtist
import by.masarnovsky.releasedemon.backend.external.dto.SpotifyUserLibraryResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SpotifyArtistsRetriever(
    val spotifyClient: SpotifyClient,
) : UserLibraryRetriever {
    override fun retrieve(username: String): List<String> {
        val userLibrary: MutableList<SpotifyArtist> = mutableListOf()
        var after: String? = null

        do {
            logger.info { "retrieve after:$after from spotify" }

            retrieveByUrl(after)?.let { response ->
                userLibrary.addAll(response.entity.items)

                after = response.entity.cursor.after
            }
        } while (after != null)

        logger.info { "found ${userLibrary.size} artists" }
        return userLibrary.map { artist -> artist.name }
    }

    private fun retrieveByUrl(after: String?): SpotifyUserLibraryResponse? {
        return spotifyClient.retrieveFollowedSpotifyArtists(after)
    }
}