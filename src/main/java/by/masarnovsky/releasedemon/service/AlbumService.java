package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.Album;

import java.util.List;

public interface AlbumService {
    List<Album> findAll();
    List<Album> findByArtist(Integer id);
    Album findById(Integer id);
    Album save(Album album);
}
