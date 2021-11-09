package by.masarnovsky.releasedemon.backend.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue
    var id: Long?,

    @Column(unique = true)
    val login: String,

    var password: String,
    var email: String?,
    var lastfmUsername: String?,
    var spotifyToken: String?,
    var telegramId: Long?,
    var created: LocalDateTime,
    var updated: LocalDateTime,
    var lastSynchronized: LocalDateTime,

    @ManyToMany(cascade = [CascadeType.ALL]) @JoinTable(
        name = "user_artist",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")]
    )
    var artists: MutableSet<Artist>,
)