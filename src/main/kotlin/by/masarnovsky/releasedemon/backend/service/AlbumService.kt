package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.Album
import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.repository.AlbumRepository
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class AlbumService(
    val repository: AlbumRepository,
) {
    fun findAll(): List<Album> {
        return repository.findAll()
    }

    fun findByArtists(artists: Set<Artist>): List<Album> {
        return repository.findAllByArtistInOrderByReleaseDateDesc(artists)
    }

    fun save(album: Album): Album {
        return repository.save(album)
    }

    fun saveAll(albums: List<Album>): List<Album> {
        return repository.saveAll(albums)
    }

    fun findByReleaseDate(date: LocalDate): List<Album> {
        return repository.findAllByReleaseDate(date)
    }

    fun findByReleaseDateBetween(from: LocalDate, to: LocalDate): List<Album> {
        return repository.findAllByReleaseDateBetween(from, to)
    }
}