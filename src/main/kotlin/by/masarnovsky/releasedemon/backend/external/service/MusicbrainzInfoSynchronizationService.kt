package by.masarnovsky.releasedemon.backend.external.service

import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.service.AlbumService
import by.masarnovsky.releasedemon.backend.service.ArtistService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class MusicbrainzInfoSynchronizationService(
    val musicbrainzClient: MusicbrainzClient,
    val artistService: ArtistService,
    val albumService: AlbumService,
) {

  fun updateArtistAlbums() {
    artistService.findAllWithMbidId().forEach { artist ->
      retrieveAlbumsForArtistByMbidWithDelay(artist)
    }
  }

  private fun retrieveAlbumsForArtistByMbidWithDelay(artist: Artist) {
    logger.info { "retrieve albums for ${artist.name}" }
    musicbrainzClient
        .retrieveAlbumsForArtistByMbid(artist.mbid!!)
        .map { release -> release.toEntityFromMusicbrainzRelease(artist) }
        .forEach { album ->
          artist.albums.add(album)
          albumService.save(album)
        }
  }
}
