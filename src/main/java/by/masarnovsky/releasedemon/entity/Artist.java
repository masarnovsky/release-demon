package by.masarnovsky.releasedemon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String uuid;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy = "artists")
    private List<User> users = new ArrayList<>();

    public void addAlbum(Album album) {
        album.setArtist(this);
        albums.add(album);
    }

    public Artist(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
