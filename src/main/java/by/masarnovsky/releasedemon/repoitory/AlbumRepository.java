package by.masarnovsky.releasedemon.repoitory;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByArtist_Id(Integer id);
    List<Album> findAlbumsByArtistInOrderByReleaseDateDesc(List<Artist> artists);
}
