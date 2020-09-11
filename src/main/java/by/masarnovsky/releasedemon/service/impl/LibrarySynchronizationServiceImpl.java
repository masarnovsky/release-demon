package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.Artist;
import by.masarnovsky.releasedemon.entity.User;
import by.masarnovsky.releasedemon.service.ArtistService;
import by.masarnovsky.releasedemon.service.LibrarySynchronizationService;
import by.masarnovsky.releasedemon.service.UserLibraryRetriever;
import by.masarnovsky.releasedemon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class LibrarySynchronizationServiceImpl implements LibrarySynchronizationService {

    private final ArtistService artistService;
    private final UserService userService;

    @Override
    @Transactional
    public void synchronizeLibrary(UserLibraryRetriever retriever) {
        User user = userService.findById(1);
        List<String> artistsNames = retriever.retrieve(user.getLastfmUsername());
        List<Artist> artists = artistService.findAllByNameIn(artistsNames);

        user.getArtists().addAll(artists);
        artists.forEach(artist -> artist.getUsers().add(user));
    }
}
