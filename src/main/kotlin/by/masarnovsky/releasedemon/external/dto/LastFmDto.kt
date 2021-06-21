package by.masarnovsky.releasedemon.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LastfmUserLibraryResponse(
    @JsonProperty("artists")
    var entity: LastfmArtistsEntity,
) {

    fun getAttributes(): LastfmUserArtistLibraryAttributes {
        return entity.attributes
    }
}

data class LastfmArtist(
    var mbid: String,
    var name: String,
)

data class LastfmArtistsEntity(
    var artist: List<LastfmArtist>,

    @JsonProperty("@attr")
    var attributes: LastfmUserArtistLibraryAttributes,
)

data class LastfmUserArtistLibraryAttributes(
    var page: Int,
    var total: Int,
    var user: String,
    var perPage: Int,
    var totalPages: Int,
)