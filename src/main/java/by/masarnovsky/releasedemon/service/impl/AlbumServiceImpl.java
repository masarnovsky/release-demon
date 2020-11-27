package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.Artist;
import by.masarnovsky.releasedemon.repository.AlbumRepository;
import by.masarnovsky.releasedemon.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository repository;

    @Override
    public List<Album> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Album> findByArtist(Integer id) {
        return repository.findByArtistId(id);
    }

    @Override
    public List<Album> findByArtists(Set<Artist> artists) {
        return repository.findAllByArtistInOrderByReleaseDateDesc(artists);
    }

    @Override
    public Album findById(Integer id) {
        return repository.findById(id).get(); // todo: resolve
    }

    @Override
    public Album save(Album album) {
        return repository.save(album);
    }

    @Override
    public List<Album> saveAll(List<Album> albums) {
        return repository.saveAll(albums);
    }

    @Override
    public List<Album> findByReleaseDate(LocalDate date) {
        return repository.findAllByReleaseDate(date);
    }

    @Override
    public List<Album> findByReleaseDateBetween(LocalDate from, LocalDate to) {
        return repository.findAllByReleaseDateBetween(from, to);
    }
}
