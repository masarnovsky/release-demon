package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.repoitory.AlbumRepository;
import by.masarnovsky.releasedemon.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.findByArtist_Id(id);
    }

    @Override
    public Album findById(Integer id) {
        return repository.findById(id).get(); // todo: resolve
    }

    @Override
    public Album save(Album album) {
        return repository.save(album);
    }

}
