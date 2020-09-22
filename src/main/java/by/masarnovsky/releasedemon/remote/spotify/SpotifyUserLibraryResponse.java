package by.masarnovsky.releasedemon.remote.spotify;

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
    private List<SpotifyArtist> items = new ArrayList<>();
    private String next;
    @JsonProperty("cursors")
    private Cursor cursor;
    private Integer total;
    private Integer limit;
}

@Getter
@Setter
class Cursor {
    private String after;
}