package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.entity.Artist;
import by.masarnovsky.releasedemon.entity.User;
import by.masarnovsky.releasedemon.service.ArtistService;
import by.masarnovsky.releasedemon.service.LibrarySynchronizationService;
import by.masarnovsky.releasedemon.service.UserLibraryRetriever;
import by.masarnovsky.releasedemon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LibrarySynchronizationServiceImpl implements LibrarySynchronizationService {

    private final ArtistService artistService;
    private final UserService userService;

    @Transactional // todo: remove
    @Override
    public void synchronizeLibrary(UserLibraryRetriever retriever) { // todo: long task
        User user = userService.findById(1);
        List<String> artistsNames = retriever.retrieve(user.getLastfmUsername());
        List<Artist> artists = artistService.findAllByNameIn(artistsNames);

        int artistsCount = user.getArtists().size();
        user.getArtists().addAll(artists);
        int updatedArtistsCount = user.getArtists().size();
        artists.forEach(artist -> artist.getUsers().add(user));
    }
}
