package by.masarnovsky.releasedemon.response;

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

