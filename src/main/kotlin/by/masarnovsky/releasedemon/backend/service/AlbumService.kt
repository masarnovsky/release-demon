package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.Album
import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.repository.AlbumRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


@Service
class AlbumService(
    val repository: AlbumRepository,
) {
    @Transactional(readOnly = true)
    fun findAll(): List<Album> {
        return repository.findAll()
    }

    @Transactional(readOnly = true)
    fun findByArtists(artists: Set<Artist>): List<Album> {
        return repository.findAllByArtistInOrderByReleaseDateDesc(artists)
    }

    @Transactional
    fun save(album: Album): Album {
        return repository.save(album)
    }

    @Transactional
    fun saveAll(albums: List<Album>): List<Album> {
        return repository.saveAll(albums)
    }

    @Transactional(readOnly = true)
    fun findByReleaseDate(date: LocalDate): List<Album> {
        return repository.findAllByReleaseDate(date)
    }

    @Transactional(readOnly = true)
    fun findByReleaseDateBetween(from: LocalDate, to: LocalDate): List<Album> {
        return repository.findAllByReleaseDateBetween(from, to)
    }
}