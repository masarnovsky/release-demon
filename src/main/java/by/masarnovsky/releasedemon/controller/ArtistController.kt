package by.masarnovsky.releasedemon.controller

import by.masarnovsky.releasedemon.dto.ArtistDTO
import by.masarnovsky.releasedemon.entity.Artist
import by.masarnovsky.releasedemon.mapper.ArtistMapper
import by.masarnovsky.releasedemon.service.ArtistService
import lombok.AllArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/artists")
@AllArgsConstructor
class ArtistController(private val artistService: ArtistService, private val artistMapper: ArtistMapper) {

    @GetMapping
    fun getAll(): List<ArtistDTO> = artistMapper.entityListToDtoList(artistService.findAll())

    @GetMapping("/{name}")
    fun getArtistByName(@PathVariable name: String): ArtistDTO {
        val artist = artistService.findByName(name)
        return artistMapper.entityToDto(artist)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody artist: ArtistDTO): Artist = artistService.save(artistMapper.dtoToEntity(artist))
}