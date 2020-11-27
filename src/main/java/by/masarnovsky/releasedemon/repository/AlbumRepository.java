package by.masarnovsky.releasedemon.repository;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByArtistId(Integer id);

    List<Album> findAllByArtistInOrderByReleaseDateDesc(Set<Artist> artists);

    List<Album> findAllByReleaseDate(LocalDate date);

    List<Album> findAllByReleaseDateBetween(LocalDate from, LocalDate to);
}
