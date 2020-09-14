package by.masarnovsky.releasedemon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mbid;

    private String title;

    private LocalDate releaseDate;

    private String type;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    public void addArtist(Artist artist) {
        artist.addAlbum(this);
        this.artist = artist;
    }
}
