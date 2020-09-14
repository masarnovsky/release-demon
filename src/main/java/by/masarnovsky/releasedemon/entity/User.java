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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    private String password;

    private String email;

    private String lastfmUsername;

    private String spotifyToken;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_artist",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id")}
    )
    private Set<Artist> artists = new HashSet<>();

    // note: for kotlin support because it doesn't work with lombok(
    // todo: remove after full kotlin migration
    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }
}
