package by.masarnovsky.releasedemon.factory;

import by.masarnovsky.releasedemon.entity.Artist;

public class ArtistFactory {

    private ArtistFactory() {
    }

    public static Artist createArtistWithMbidId(String name, String mbid) {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setMbid(mbid);
        return artist;
    }

    public static Artist createArtistWithSpotifyId(String name, String spotifyId) {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setSpotifyId(spotifyId);
        return artist;
    }
}
