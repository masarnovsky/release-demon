package by.masarnovsky.releasedemon.controller;

import by.masarnovsky.releasedemon.dto.ArtistDTO;
import by.masarnovsky.releasedemon.entity.Artist;
import by.masarnovsky.releasedemon.mapper.ArtistMapper;
import by.masarnovsky.releasedemon.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@AllArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @GetMapping
    public List<ArtistDTO> getAll() {
        return artistMapper.entityListToDtoList(artistService.findAll());
    }

    @GetMapping("/{name}")
    public ArtistDTO getArtistByName(@PathVariable String name) {
        Artist artist = artistService.findByName(name);
        return artistMapper.entityToDto(artist);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist save(@RequestBody ArtistDTO artist) {
        return artistService.save(artistMapper.dtoToEntity(artist));
    }

}
