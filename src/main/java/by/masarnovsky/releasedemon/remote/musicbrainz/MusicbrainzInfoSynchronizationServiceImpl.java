package by.masarnovsky.releasedemon.remote.musicbrainz;

import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.Artist;
import by.masarnovsky.releasedemon.service.AlbumService;
import by.masarnovsky.releasedemon.service.ArtistService;
import by.masarnovsky.releasedemon.service.InfoSynchromizationService;
import by.masarnovsky.releasedemon.util.TimeUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MusicbrainzInfoSynchronizationServiceImpl implements InfoSynchromizationService {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final static String RETRIEVE_ALBUMS_URL = "http://musicbrainz.org/ws/2/artist/%s?inc=release-groups&fmt=json";
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(MusicbrainzInfoSynchronizationServiceImpl.class);
    private static final Integer LIMIT = 1;

    @Override
    public boolean updateArtistAlbums() {
        List<Artist> artists = artistService.findAllWithMbidId();
        int count = 1;

        for (Artist artist : artists) {
            sleep(count++);
            logger.info("retrieve albums for " + artist.getName());
            List<MusicbrainzRelease> retrievedAlbums = retrieveArtistRelease(artist.getMbid());
            Map<String, MusicbrainzRelease> releaseMap = retrievedAlbums.stream().collect(Collectors.toMap(MusicbrainzRelease::getId, Function.identity()));

            Set<Album> albums = artist.getAlbums();
            if (retrievedAlbums.size() > albums.size()) {
                logger.info("update albums for " + artist.getName());
                albums.forEach(album -> releaseMap.remove(album.getMbid()));
                releaseMap.values().forEach(newAlbum -> {
                    Album e = new Album();
                    e.setMbid(newAlbum.getId());
                    e.setTitle(newAlbum.getTitle());
                    e.setType(newAlbum.getPrimaryType());

                    String releaseDate = newAlbum.getFirstReleaseDate();
                    e.setReleaseDate(TimeUtils.convertStringToDate(releaseDate, "yyyy-MM-dd"));

                    e.addArtist(artist);
                    albumService.save(e);
                });
            }
        }

        return true;
    }

    private List<MusicbrainzRelease> retrieveArtistRelease(String mbid) {
        MusicbrainzResponse response = restTemplate.getForObject(String.format(RETRIEVE_ALBUMS_URL, mbid), MusicbrainzResponse.class);
        return response == null ? new ArrayList<>() : response.getReleaseGroups();
    }

    private void sleep(int count) {
        if (count % LIMIT == 0) {
            try {
                logger.info("thread sleep to prevent exceeding of request rate limit");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.warn("thread was interrupted");
            }
        }
    }
}
