package by.masarnovsky.releasedemon.client;

import by.masarnovsky.releasedemon.lastfm.LastfmUserLibraryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "lastfmclient", url = "http://ws.audioscrobbler.com/2.0")
public interface LastfmClient {

    @RequestMapping(method = RequestMethod.GET, value = "/?method=library.getartists&format=json&")
    LastfmUserLibraryResponse retrievePageWithArtists(@RequestParam("api_key") String apiKey, @RequestParam("user") String user, @RequestParam("page") Integer page);
}
