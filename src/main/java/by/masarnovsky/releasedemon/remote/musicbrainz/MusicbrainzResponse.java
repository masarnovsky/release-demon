package by.masarnovsky.releasedemon.remote.musicbrainz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class MusicbrainzResponse {
    private String id;
    private String name;

    @JsonProperty("release-groups")
    private List<MusicbrainzRelease> releaseGroups = new ArrayList<>();
}

@Data
@Getter
class MusicbrainzRelease {
    @JsonProperty("first-release-date")
    private String firstReleaseDate;
    private String title;
    @JsonProperty("primary-type")
    private String primaryType;
    private String id;
}

