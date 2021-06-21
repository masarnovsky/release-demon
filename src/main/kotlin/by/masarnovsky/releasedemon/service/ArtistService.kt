package by.masarnovsky.releasedemon.service

import by.masarnovsky.releasedemon.entity.Artist
import by.masarnovsky.releasedemon.repository.ArtistRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashSet

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