package by.masarnovsky.releasedemon.backend.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "artists")
class Artist(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    val name: String,
    var mbid: String? = null,
    @OneToMany(mappedBy = "artist") var albums: MutableSet<Album> = mutableSetOf(),
    @ManyToMany(mappedBy = "artists") var users: MutableSet<User> = mutableSetOf(),
) {

  fun addAlbum(album: Album) {
    album.artist = this
    albums.add(album)
  }

  fun toDTO(): ArtistDTO {
    return ArtistDTO(this.id, this.name, this.mbid, this.albums.map { it.toDTO() })
  }
}

data class ArtistDTO(
    var id: Long?,
    val name: String,
    var mbid: String?,
    val albums: List<AlbumDTO>
)
