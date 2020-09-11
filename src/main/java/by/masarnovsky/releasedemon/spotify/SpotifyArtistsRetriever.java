package by.masarnovsky.releasedemon.spotify;

import by.masarnovsky.releasedemon.service.UserLibraryRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyArtistsRetriever implements UserLibraryRetriever {

    private final RestTemplate restTemplate = new RestTemplate();
    Logger logger = LoggerFactory.getLogger(SpotifyArtistsRetriever.class);

    @Value("${spotify.token}")
    private String token;


    @Override
    public List<String> retrieve(String username) {
        List<SpotifyArtist> userLibrary = new ArrayList<>();

        String url = "https://api.spotify.com/v1/me/following?type=artist&limit=10";
        String next = null;
        Integer counter = 0; // todo: replace by next variable
        do {
            SpotifyUserLibraryResponse response = retrieveByUrl(url);
            userLibrary.addAll(response.getEntity().getItems());

            next = response.getEntity().getNext();
        } while (counter++ < 3);
//        } while (next != null);

        List<String> artistsNames = new ArrayList<>();
        userLibrary.forEach(artist -> artistsNames.add(artist.getName()));
        return artistsNames;
    }

    private SpotifyUserLibraryResponse retrieveByUrl(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<SpotifyUserLibraryResponse> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, SpotifyUserLibraryResponse.class);
        return exchange.getBody();
    }
}
