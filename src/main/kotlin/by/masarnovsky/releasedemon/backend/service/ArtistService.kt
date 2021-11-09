package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.repository.ArtistRepository
import org.springframework.stereotype.Service

@Service
class ArtistService(
    val repository: ArtistRepository,
) {
    fun findAll(): Set<Artist> {
        return HashSet(repository.findAll())
    }

    fun findByName(name: String): Artist? {
        return repository.findByName(name)
    }

    fun save(artist: Artist): Artist {
        return repository.save(artist)
    }

    fun saveAll(artists: List<Artist>): List<Artist> {
        return repository.saveAll(artists)
    }

    fun findAllByNameIn(artists: List<String>): List<Artist> {
        return repository.findAllByNameIn(artists)
    }

    fun findAllWithMbidId(): List<Artist> {
        return repository.findAllByMbidIsNotNull()
    }
}