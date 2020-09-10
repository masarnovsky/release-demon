package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.Artist;

import java.util.List;

public interface AlbumService {
    List<Album> findAll();
    List<Album> findByArtist(Integer id);
    List<Album> findByArtists(List<Artist> artists);
    Album findById(Integer id);
    Album save(Album album);
    List<Album> saveAll(List<Album> albums);
}
