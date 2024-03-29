package by.masarnovsky.releasedemon.backend.entity

import com.elbekD.bot.types.Message
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var lastfmUsername: String? = null,
    var telegramId: Long? = null,
    var lastSynchronized: LocalDateTime? = null,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_artist",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")])
    var artists: MutableSet<Artist> = mutableSetOf(),
) {

  companion object {
    fun fromMessage(message: Message) =
        User(
            telegramId = message.chat.id,
        )
  }

  override fun toString(): String {
    return "User(id=$id, lastfmUsername=$lastfmUsername, telegramId=$telegramId)"
  }
}
