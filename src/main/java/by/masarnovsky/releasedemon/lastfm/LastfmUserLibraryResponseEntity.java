package by.masarnovsky.releasedemon.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;


//todo: remove data annotations

@Data
@Getter
@Setter
public class LastfmUserLibraryResponseEntity {
    @JsonProperty("artists")
    private LastfmArtistsEntity entity;

    public LastfmUserArtistLibraryAttributes getAttributes() {
        return entity.getAttributes();
    }
}

@Data
@Getter
@Setter
class LastfmArtistEntity {
    private String mbid;
    private String name;
}

@Data
@Getter
@Setter
class LastfmArtistsEntity {
    private List<LastfmArtistEntity> artist = new ArrayList<>();

    @JsonProperty("@attr")
    private LastfmUserArtistLibraryAttributes attributes;
}

@Data
@Getter
@Setter
class LastfmUserArtistLibraryAttributes {
    private Integer page;
    private Integer total;
    private String user;
    private Integer perPage;
    private Integer totalPages;
}