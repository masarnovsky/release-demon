package by.masarnovsky.releasedemon.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MusicbrainzRelease {
    @JsonProperty("first-release-date")
    private String firstReleaseDate;
    private String title;
    @JsonProperty("primary-type")
    private String primaryType;
    private String id;
}
