package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.external.dto.MusicbrainzRelease
import by.masarnovsky.releasedemon.backend.external.dto.MusicbrainzResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import org.springframework.stereotype.Service

@Service
class MusicbrainzClient {

  fun retrieveAlbumsForArtistByMbid(mbid: String): List<MusicbrainzRelease> {
    val musicbrainzUrl = "http://musicbrainz.org/ws/2/artist/$mbid?inc=release-groups&fmt=json"

    val (_, _, result) = musicbrainzUrl.httpGet().responseObject<MusicbrainzResponse>()

    return result.component1()?.releaseGroups ?: listOf()
  }
}
