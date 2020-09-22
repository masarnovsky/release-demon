package by.masarnovsky.releasedemon.remote.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LastfmUserLibraryResponse {
    @JsonProperty("artists")
    private LastfmArtistsEntity entity;

    public LastfmUserArtistLibraryAttributes getAttributes() {
        return entity.getAttributes();
    }
}

@Getter
@Setter
class LastfmArtist {
    private String mbid;
    private String name;
}

@Getter
@Setter
class LastfmArtistsEntity {
    private List<LastfmArtist> artist = new ArrayList<>();

    @JsonProperty("@attr")
    private LastfmUserArtistLibraryAttributes attributes;
}

@Getter
@Setter
class LastfmUserArtistLibraryAttributes {
    private Integer page;
    private Integer total;
    private String user;
    private Integer perPage;
    private Integer totalPages;
}