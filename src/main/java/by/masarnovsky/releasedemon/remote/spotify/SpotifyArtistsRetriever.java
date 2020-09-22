package by.masarnovsky.releasedemon.remote.spotify;

import by.masarnovsky.releasedemon.client.SpotifyClient;
import by.masarnovsky.releasedemon.service.UserLibraryRetriever;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyArtistsRetriever implements UserLibraryRetriever {

    @Value("${spotify.token}")
    private String token;

    @NonNull
    private final SpotifyClient spotifyClient;

    private final Logger logger = LoggerFactory.getLogger(SpotifyArtistsRetriever.class);

    @Override
    public List<String> retrieve(String username) {
        List<SpotifyArtist> userLibrary = new ArrayList<>();

        String after = null;
        do {
            SpotifyUserLibraryResponse response = retrieveByUrl(after);
            userLibrary.addAll(response.getEntity().getItems());

            after = response.getEntity().getCursor().getAfter();
        } while (after != null);

        List<String> artistsNames = new ArrayList<>();
        userLibrary.forEach(artist -> artistsNames.add(artist.getName()));
        return artistsNames;
    }

    private SpotifyUserLibraryResponse retrieveByUrl(String after) {
        return spotifyClient.retrieveFollowedSpotifyArtists(10, after, token);
    }
}
