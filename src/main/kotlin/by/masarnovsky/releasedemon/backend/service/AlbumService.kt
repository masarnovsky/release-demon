package by.masarnovsky.releasedemon.backend.service

import by.masarnovsky.releasedemon.backend.entity.Album
import by.masarnovsky.releasedemon.backend.repository.AlbumRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumService(
    val repository: AlbumRepository,
) {
  @Transactional
  fun save(album: Album): Album {
    return repository.save(album)
  }
}
