package by.masarnovsky.releasedemon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String mbid;

    private String spotifyId;

    @OneToMany(mappedBy = "artist")
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(mappedBy = "artists")
    private Set<User> users = new HashSet<>();

    public void addAlbum(Album album) {
        album.setArtist(this);
        albums.add(album);
    }
}
