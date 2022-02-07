package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.repository.ArtistRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArtistService(
    val repository: ArtistRepository,
) {
    @Transactional(readOnly = true)
    fun findAll(): Set<Artist> {
        return HashSet(repository.findAll())
    }

    @Transactional(readOnly = true)
    fun findByName(name: String): Artist? {
        return repository.findByName(name)
    }

    @Transactional
    fun save(artist: Artist): Artist {
        return repository.save(artist)
    }

    @Transactional(readOnly = true)
    fun findAllByNameIn(artists: List<String>): List<Artist> {
        return repository.findAllByNameIn(artists)
    }

    @Transactional(readOnly = true)
    fun findAllWithMbidId(): List<Artist> {
        return repository.findAllByMbidIsNotNull()
    }

    @Transactional(readOnly = true)
    fun findUserArtistsByTelegramId(chatId: Long): Set<Artist> {
        return repository.findAllByUsers_TelegramId(chatId)
    }

    @Transactional
    fun saveAll(artists: List<Artist>): MutableList<Artist> {
        return repository.saveAll(artists)
    }
}