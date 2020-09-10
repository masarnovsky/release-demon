package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    List<Artist> findAll();
    Optional<Artist> findById(Integer id);
    Artist findByName(String name);
    Artist save(Artist artist);
    List<Artist> saveAll(List<Artist> artists);
    List<Artist> findAllByNameIn(List<String> artist);
}
