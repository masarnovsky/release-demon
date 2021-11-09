package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.SpotifyArtist
import by.masarnovsky.releasedemon.backend.external.dto.SpotifyUserLibraryResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SpotifyArtistsRetriever : UserLibraryRetriever {

    @Value("\${spotify.token}")
    lateinit var token: String
    var limit = 10

    override fun retrieve(identifier: String): List<String> {
        val userLibrary: MutableList<SpotifyArtist> = mutableListOf()
        var after: String? = null

        do {
            logger.info { "retrieve after:$after from spotify" }

            retrievePageAfter(after)?.let { response ->
                userLibrary.addAll(response.entity.items)

                after = response.entity.cursor.after
            }
        } while (after != null)

        logger.info { "found ${userLibrary.size} artists" }
        return userLibrary.map { artist -> artist.name }
    }

    private fun retrievePageAfter(after: String?): SpotifyUserLibraryResponse? {
        var spotifyUrl = "https://api.spotify.com/v1/me/following?type=artist&limit=$limit"

        if (after != null) spotifyUrl += "&after=$after"


        val (_, _, result) = spotifyUrl
            .httpGet()
            .header(mapOf("Accept" to "application/json", "Authorization" to token))
            .responseObject<SpotifyUserLibraryResponse>()

        return result.component1()
    }
}