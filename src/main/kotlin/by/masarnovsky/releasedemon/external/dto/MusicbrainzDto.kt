package by.masarnovsky.releasedemon.external.dto

import by.masarnovsky.releasedemon.entity.Album
import by.masarnovsky.releasedemon.entity.Artist
import by.masarnovsky.releasedemon.utils.tryDate
import com.fasterxml.jackson.annotation.JsonProperty

data class MusicbrainzResponse(
    val id: String,
    val name: String,

    @JsonProperty("release-groups")
    val releaseGroups: List<MusicbrainzRelease>,
)

data class MusicbrainzRelease(
    @JsonProperty("first-release-date")
    val firstReleaseDate: String,
    val title: String,

    @JsonProperty("primary-type")
    val primaryType: String,
    val id: String,
) {
    fun toEntityFromMusicbrainzRelease(artist: Artist): Album {
        return Album(
            null,
            id,
            title,
            tryDate(firstReleaseDate),
            primaryType,
            artist,
        )
    }
}

