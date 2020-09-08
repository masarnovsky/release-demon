package by.masarnovsky.releasedemon.controller

import by.masarnovsky.releasedemon.dto.AlbumDTO
import by.masarnovsky.releasedemon.mapper.AlbumMapper
import by.masarnovsky.releasedemon.service.AlbumService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/albums")
class AlbumController(private val albumService: AlbumService, private val albumMapper: AlbumMapper) {
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
}