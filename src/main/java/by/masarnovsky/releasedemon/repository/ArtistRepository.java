package by.masarnovsky.releasedemon.repository;

import by.masarnovsky.releasedemon.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Artist findByName(String name);
}
