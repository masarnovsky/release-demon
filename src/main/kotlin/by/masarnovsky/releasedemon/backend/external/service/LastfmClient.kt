package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.LastfmUserLibraryResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LastfmClient {

    @Value("\${last.fm.api.key}")
    lateinit var apiKey: String

    fun retrievePageWithArtists(username: String, page: Int): LastfmUserLibraryResponse? {
        val lastfmUrl =
            "https://ws.audioscrobbler.com/2.0/?method=library.getartists&format=json&api_key=$apiKey&user=$username&page=$page"

        val (_, _, result) = lastfmUrl
            .httpGet()
            .responseObject<LastfmUserLibraryResponse>()

        return result.component1()
    }
}