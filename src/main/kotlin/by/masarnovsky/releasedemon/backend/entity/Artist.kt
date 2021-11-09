package by.masarnovsky.releasedemon.backend.entity

import javax.persistence.*

@Entity
class Artist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    val name: String,
    var mbid: String?,
    var spotifyId: String?,

    @OneToMany(mappedBy = "artist")
    var albums: MutableSet<Album>,

    @ManyToMany(mappedBy = "artists")
    var users: MutableSet<User>,
) {

    fun addAlbum(album: Album) {
        album.artist = this
        albums.add(album)
    }

    fun toDTO(): ArtistDTO {
        return ArtistDTO(
            this.id,
            this.name,
            this.mbid,
            this.albums.map { it.toDTO() })
    }
}

data class ArtistDTO(
    var id: Long?,
    val name: String,
    var mbid: String?,
    val albums: List<AlbumDTO>
)