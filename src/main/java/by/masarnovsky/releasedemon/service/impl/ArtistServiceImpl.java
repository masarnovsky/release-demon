package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.Artist;
import by.masarnovsky.releasedemon.repository.ArtistRepository;
import by.masarnovsky.releasedemon.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;

    @Override
    public List<Artist> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Artist> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Artist findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Artist save(Artist artist) {
        return repository.save(artist);
    }

    @Override
    public List<Artist> saveAll(List<Artist> artists) {
        return repository.saveAll(artists);
    }

    @Override
    public List<Artist> findAllByNameIn(List<String> artists) {
        return repository.findAllByNameIn(artists);
    }
}
