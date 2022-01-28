package by.masarnovsky.releasedemon.backend.entity

import com.elbekD.bot.types.Message
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var lastfmUsername: String? = null,
    var telegramId: Long? = null,
    var lastSynchronized: LocalDateTime? = null,

    @ManyToMany(cascade = [CascadeType.ALL]) @JoinTable(
        name = "user_artist",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")]
    )
    var artists: MutableSet<Artist> = mutableSetOf(),
) {


    companion object {
        fun fromMessage(message: Message) = User(
            telegramId = message.chat.id,
        )
    }
}