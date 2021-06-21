package by.masarnovsky.releasedemon.controller

import by.masarnovsky.releasedemon.entity.Artist
import by.masarnovsky.releasedemon.entity.ArtistDTO
import by.masarnovsky.releasedemon.service.ArtistService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/artists")
class ArtistController(
    val artistService: ArtistService,
) {

    @GetMapping
    fun getAllArtists(): Set<Artist> = artistService.findAll()

    @GetMapping("/{name}")
    fun getArtistByName(@PathVariable name: String): ArtistDTO? {
        return artistService.findByName(name)?.toDTO()
    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun save(@RequestBody artist: ArtistDTO): Artist = artistService.save(artistMapper.dtoToEntity(artist))
}