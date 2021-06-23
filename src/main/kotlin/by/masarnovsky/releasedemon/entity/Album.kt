package by.masarnovsky.releasedemon.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.*


@Entity
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var mbid: String?,
    var title: String,
    var releaseDate: LocalDate,
    var type: String?,

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    var artist: Artist,
) {
    fun toDTO(): AlbumDTO {
        return AlbumDTO(this.id, this.mbid, this.title, this.releaseDate, this.artist.id, this.artist.name, this.type)
    }

    override fun equals(other: Any?): Boolean {
       return  if (other is Album) {
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
    var id: Int?,
    var mbid: String?,
    val title: String,
    val releaseDate: LocalDate,
    val artistId: Int?,
    val artistName: String,
    var type: String?,
)
