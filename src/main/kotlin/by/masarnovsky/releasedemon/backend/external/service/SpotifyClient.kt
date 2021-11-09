package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.SpotifyUserLibraryResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SpotifyClient {

    @Value("\${spotify.token}")
    lateinit var token: String
    var limit = 10

    fun retrieveFollowedSpotifyArtists(after: String?): SpotifyUserLibraryResponse? {
        var spotifyUrl = "https://api.spotify.com/v1/me/following?type=artist&limit=$limit"

        if (after != null) spotifyUrl += "&after=$after"


        val (_, _, result) = spotifyUrl
            .httpGet()
            .header(mapOf("Accept" to "application/json", "Authorization" to token))
            .responseObject<SpotifyUserLibraryResponse>()

        return result.component1()
    }
}