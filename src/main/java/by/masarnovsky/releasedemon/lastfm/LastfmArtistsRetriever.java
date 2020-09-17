package by.masarnovsky.releasedemon.lastfm;

import by.masarnovsky.releasedemon.client.LastfmClient;
import by.masarnovsky.releasedemon.service.UserLibraryRetriever;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LastfmArtistsRetriever implements UserLibraryRetriever {

    @Value("${last.fm.api.key}")
    private String apiKey;

    @NonNull
    private final LastfmClient lastfmClient;

    private final Logger logger = LoggerFactory.getLogger(LastfmArtistsRetriever.class);

    @Override
    public List<String> retrieve(String username) {
        Integer page = 1;
        Integer totalPage = 1;
        List<LastfmArtist> userLibrary = new ArrayList<>();
        do {
            logger.info(String.format("---------- retrieve artists for %s from page %d/%d ----------", username, page, totalPage));
            LastfmUserLibraryResponse artists = retrieveForPage(username, page);
            userLibrary.addAll(artists.getEntity().getArtist());
            totalPage = artists.getAttributes().getTotalPages(); // todo: use that variable
        } while (++page <= 3);

        List<String> artistsNames = new ArrayList<>();
        userLibrary.forEach(artist -> artistsNames.add(artist.getName()));

        return artistsNames;
    }

    private LastfmUserLibraryResponse retrieveForPage(String username, Integer page) {

        return lastfmClient.retrievePageWithArtists(apiKey, username, page);
    }
}
