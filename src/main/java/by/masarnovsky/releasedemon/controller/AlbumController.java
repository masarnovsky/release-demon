package by.masarnovsky.releasedemon.controller;

import by.masarnovsky.releasedemon.dto.AlbumDTO;
import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.mapper.AlbumMapper;
import by.masarnovsky.releasedemon.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    @GetMapping("/{id}")
    AlbumDTO findAlbumById(@PathVariable Integer id) {
        Album album = albumService.findById(id);
        return albumMapper.entityToDto(album);
    }

    @PostMapping
    AlbumDTO save(@RequestBody AlbumDTO dto) {
        Album newAlbum = albumService.save(albumMapper.dtoToEntity(dto));
        return albumMapper.entityToDto(newAlbum);
    }
}
