package by.masarnovsky.releasedemon.service;

import by.masarnovsky.releasedemon.entity.Artist;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ArtistService {
    Set<Artist> findAll();

    Optional<Artist> findById(Integer id);

    Artist findByName(String name);

    Artist save(Artist artist);

    List<Artist> saveAll(List<Artist> artists);

    List<Artist> findAllByNameIn(List<String> artist);

    List<Artist> findAllWithMbidId();
}
