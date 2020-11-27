package by.masarnovsky.releasedemon.repository;

import by.masarnovsky.releasedemon.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Artist findByName(String name);

    List<Artist> findAllByNameIn(List<String> artist);

    List<Artist> findAllByMbidIsNotNull();
}
