package by.masarnovsky.releasedemon.backend.repository

import by.masarnovsky.releasedemon.backend.entity.Album
import by.masarnovsky.releasedemon.backend.entity.Artist
import by.masarnovsky.releasedemon.backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumRepository : JpaRepository<Album, Long> {
}

@Repository
interface ArtistRepository : JpaRepository<Artist, Long> {
    fun findByName(name: String): Artist?
    fun findAllByNameIn(artist: List<String>): List<Artist>
    fun findAllByMbidIsNotNull(): List<Artist>
}

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByTelegramId(telegramId: Long): User?
    fun findByLastfmUsername(username: String): User?
    fun findAllByLastfmUsernameNotNull(): List<User>
}