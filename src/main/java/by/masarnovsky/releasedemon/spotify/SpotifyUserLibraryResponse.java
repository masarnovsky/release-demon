package by.masarnovsky.releasedemon.spotify;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SpotifyUserLibraryResponse {
    @JsonProperty("artists")
    private SpotifyArtistsEntity entity;
}

@Getter
@Setter
class SpotifyArtist {
    private String id;
    private String name;
}

@Getter
@Setter
class SpotifyArtistsEntity {
    List<SpotifyArtist> items = new ArrayList<>();
    private String next;
    private float total;
    private float limit;

}