package by.masarnovsky.releasedemon.controller

import by.masarnovsky.releasedemon.dto.AlbumDTO
import by.masarnovsky.releasedemon.dto.ArtistDTO
import by.masarnovsky.releasedemon.mapper.AlbumMapper
import by.masarnovsky.releasedemon.mapper.ArtistMapper
import by.masarnovsky.releasedemon.service.AlbumService
import by.masarnovsky.releasedemon.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService, private val artistMapper: ArtistMapper,
                     private val albumService: AlbumService, private val albumMapper: AlbumMapper) {

    @GetMapping("/{login}/artists")
    fun findAllUserArtists(@PathVariable login: String): List<ArtistDTO> {
        val user = userService.findByLogin(login)
        return artistMapper.entityListToDtoList(user.artists)
    }

    @GetMapping("/{login}/albums")
    fun findAllUserAlbums(@PathVariable login: String): List<AlbumDTO> {
        val user = userService.findByLogin(login)
        val albums = albumService.findByArtists(user.artists)
        val albumsDto = mutableListOf<AlbumDTO>()
        for (album in albums) {
            albumsDto.add(albumMapper.entityToDto(album))
        }
        return albumsDto
    }
}