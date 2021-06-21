package by.masarnovsky.releasedemon.entity

import java.time.LocalDate
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
