package by.masarnovsky.releasedemon.controller

import by.masarnovsky.releasedemon.dto.AlbumDTO
import by.masarnovsky.releasedemon.mapper.AlbumMapper
import by.masarnovsky.releasedemon.service.AlbumService
import by.masarnovsky.releasedemon.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/albums")
class AlbumController(private val albumService: AlbumService,
                      private val albumMapper: AlbumMapper,
                      private val userService: UserService) {
    @GetMapping("/{id}")
    fun findAlbumById(@PathVariable id: Int): AlbumDTO {
        val album = albumService.findById(id)
        return albumMapper.entityToDto(album)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody dto: AlbumDTO): AlbumDTO {
        val newAlbum = albumService.save(albumMapper.dtoToEntity(dto))
        return albumMapper.entityToDto(newAlbum)
    }

    @GetMapping
    fun findAlbumsForUser(@RequestParam login: String): List<AlbumDTO> {
        val user = userService.findByLogin(login)
        val albums = albumService.findByArtists(user.artists)
        return albumMapper.entitySetToDtoSet(albums)
    }
}