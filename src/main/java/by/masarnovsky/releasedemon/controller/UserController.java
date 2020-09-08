package by.masarnovsky.releasedemon.controller;

import by.masarnovsky.releasedemon.dto.AlbumDTO;
import by.masarnovsky.releasedemon.dto.ArtistDTO;
import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.User;
import by.masarnovsky.releasedemon.mapper.AlbumMapper;
import by.masarnovsky.releasedemon.mapper.ArtistMapper;
import by.masarnovsky.releasedemon.service.AlbumService;
import by.masarnovsky.releasedemon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AlbumService albumService;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;

    @GetMapping("/{login}/artists")
    public List<ArtistDTO> findAllUserArtists(@PathVariable String login)  {
        User user = userService.findByLogin(login);
        return artistMapper.entityListToDtoList(user.getArtists());
    }

    @GetMapping("/{login}/albums")
    public List<AlbumDTO> findAllUserAlbums(@PathVariable  String login)  {
        User user = userService.findByLogin(login);
        List<Album> albums = albumService.findByArtists(user.getArtists());
        List<AlbumDTO> albumsDto = new ArrayList<>();
        for (Album a : albums) {
            albumsDto.add(albumMapper.entityToDto(a));
        }
        return albumsDto;
    }
}
