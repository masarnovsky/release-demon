package by.masarnovsky.releasedemon.client;

import by.masarnovsky.releasedemon.spotify.SpotifyUserLibraryResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spotifyclient", url = "https://api.spotify.com/v1")
public interface SpotifyClient {

    @Headers("Bearer: {token}")
    @RequestMapping(method = RequestMethod.GET, value = "/me/following?type=artist&")
    SpotifyUserLibraryResponse retrieveFollowedSpotifyArtists(@RequestParam("limit") Integer limit, @RequestParam("after") String after, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
