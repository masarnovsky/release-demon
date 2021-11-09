package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.LastfmArtist
import by.masarnovsky.releasedemon.backend.external.dto.LastfmUserLibraryResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class LastFmLibraryRetriever(
    val lastFmClient: LastfmClient,
) : UserLibraryRetriever {

    override fun retrieve(username: String): List<String> {
        var page = 1
        var totalPage = 10
        val userLibrary: MutableList<LastfmArtist> = mutableListOf()

        do {
            logger.info { "retrieve $page/$totalPage page for $username from last.fm" }

            retrieveForPage(username, page)?.let { response ->
                userLibrary.addAll(response.entity.artist)
                totalPage = response.getAttributes().totalPages
            }
        } while (++page <= totalPage)

        logger.info { "found ${userLibrary.size} artists" }
        return userLibrary.map { artist -> artist.name }
    }

    private fun retrieveForPage(username: String, page: Int): LastfmUserLibraryResponse? {
        return lastFmClient.retrievePageWithArtists(username, page)
    }
}