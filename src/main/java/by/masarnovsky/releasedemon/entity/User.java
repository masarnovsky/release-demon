package by.masarnovsky.releasedemon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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

    private Long telegramId;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime lastSynchronized;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
