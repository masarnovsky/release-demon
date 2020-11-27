package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.Artist;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AlbumService {
    List<Album> findAll();

    List<Album> findByArtist(Integer id);

    List<Album> findByArtists(Set<Artist> artists);

    Album findById(Integer id);

    Album save(Album album);

    List<Album> saveAll(List<Album> albums);

    List<Album> findByReleaseDate(LocalDate date);

    List<Album> findByReleaseDateBetween(LocalDate from, LocalDate to);
}
