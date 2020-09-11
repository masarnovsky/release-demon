package by.masarnovsky.releasedemon.lastfm;

import by.masarnovsky.releasedemon.service.UserLibraryRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LastfmArtistsRetriever implements UserLibraryRetriever {

    @Value("${last.fm.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    Logger logger = LoggerFactory.getLogger(LastfmArtistsRetriever.class);


    private final static String RETRIEVE_USER_LIBRARY = "http://ws.audioscrobbler.com/2.0/?method=library.getartists&api_key=%s&user=%s&format=json&page=%d";

    @Override
    public List<String> retrieve(String username) {
        Integer page = 1;
        Integer totalPage = 1;
        List<LastfmArtist> userLibrary = new ArrayList<>();
        do {
            logger.info(String.format("---------- retrieve artists from page %d/%d ----------", page, totalPage));
            LastfmUserLibraryResponse artists = retrieveForPage(username, page);
            userLibrary.addAll(artists.getEntity().getArtist());
            totalPage = artists.getAttributes().getTotalPages(); // todo: use that variable
        } while (++page <= 3);

        List<String> artistsNames = new ArrayList<>();
        userLibrary.forEach(artist -> artistsNames.add(artist.getName()));

        // todo in external service
        // get artists from lastfm
        // get artists from db (from prev. step)
        // set for user and artist value in many-to-many list
        // update in database


        return artistsNames;
    }

    private LastfmUserLibraryResponse retrieveForPage(String username, Integer page) {

        return restTemplate.getForObject(String.format(RETRIEVE_USER_LIBRARY, apiKey, username, page), LastfmUserLibraryResponse.class);
    }
}
