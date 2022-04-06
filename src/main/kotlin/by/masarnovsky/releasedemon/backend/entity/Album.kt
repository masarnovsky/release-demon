package by.masarnovsky.releasedemon.backend.entity

import java.time.LocalDate
import java.util.Objects
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "albums")
class Album(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,
    var mbid: String?,
    var title: String,
    var releaseDate: LocalDate,
    var type: String?,
    @ManyToOne @JoinColumn(name = "artist_id", nullable = false) var artist: Artist,
) {
  fun toDTO(): AlbumDTO {
    return AlbumDTO(
        this.id,
        this.mbid,
        this.title,
        this.releaseDate,
        this.artist.id,
        this.artist.name,
        this.type)
  }

  override fun equals(other: Any?): Boolean {
    return if (other is Album) {
      return title == other.title && releaseDate == other.releaseDate
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return Objects.hash(title, releaseDate)
  }
}

data class AlbumDTO(
    var id: Long?,
    var mbid: String?,
    val title: String,
    val releaseDate: LocalDate,
    val artistId: Long?,
    val artistName: String,
    var type: String?,
)
