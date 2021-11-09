package by.masarnovsky.releasedemon.backend.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotifyUserLibraryResponse(
    @JsonProperty("artists")
    val entity: SpotifyArtistsEntity,
)

data class SpotifyArtist(
    val id: String,
    val name: String,
)

data class SpotifyArtistsEntity(
    val items: List<SpotifyArtist>,
    val next: String,

    @JsonProperty("cursors")
    val cursor: Cursor,
    val total: Int,
    val limit: Int,
)

data class Cursor(
    val after: String?
)