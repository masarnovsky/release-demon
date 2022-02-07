package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.LastfmArtist
import by.masarnovsky.releasedemon.backend.external.dto.LastfmUserLibraryResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class LastFmLibraryRetriever : UserLibraryRetriever {

    @Value("\${last.fm.api.key}")
    lateinit var apiKey: String

    override fun retrieve(identifier: String): List<String> {
        var page = 1
        var totalPage = 10
        val userLibrary: MutableList<LastfmArtist> = mutableListOf()

        do {
            logger.info { "retrieve $page/$totalPage page for username=$identifier from last.fm" }

            retrievePage(identifier, page)?.let { response ->
                userLibrary.addAll(response.entity.artist)
                totalPage = response.getAttributes().totalPages
            }
        } while (++page <= totalPage)

        logger.info { "found ${userLibrary.size} artists" }
        return userLibrary.map { artist -> artist.name }
    }

    private fun retrievePage(username: String, page: Int): LastfmUserLibraryResponse? {
        val lastfmUrl =
            "https://ws.audioscrobbler.com/2.0/?method=library.getartists&format=json&api_key=$apiKey&user=$username&page=$page"

        val (_, _, result) = lastfmUrl
            .httpGet()
            .responseObject<LastfmUserLibraryResponse>()

        return result.component1()
    }
}