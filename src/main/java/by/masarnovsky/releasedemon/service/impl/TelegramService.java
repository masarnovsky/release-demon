package by.masarnovsky.releasedemon.service.impl;

import by.masarnovsky.releasedemon.client.TelegramClient;
import by.masarnovsky.releasedemon.entity.Album;
import by.masarnovsky.releasedemon.entity.User;
import by.masarnovsky.releasedemon.service.AlbumService;
import by.masarnovsky.releasedemon.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @NonNull
    private final UserService userService;

    @NonNull
    private final AlbumService albumService;

    @NonNull
    private final TelegramClient telegramClient;

    public void notifyUsers() { // todo: long task
        List<Album> todayReleases = albumService.findByReleaseDate(LocalDate.now());
        List<User> users = userService.findAllTelegramUsers();

        Map<User, List<Album>> updatesForUser = new HashMap<>();

        todayReleases.forEach(album -> {
            album.getArtist().getUsers().stream().filter(user -> user.getTelegramId() != null).forEach(user -> {
                updatesForUser.compute(user, (k, v) -> {
                    List<Album> albums = v == null ? new ArrayList<>() : v;
                    albums.add(album);
                    return albums;
                });
            });
        });
        updatesForUser.forEach((k, v) -> {
            String message = formatMessage(v);
            sendMessage(k.getTelegramId(), message);
        });
    }

    private String formatMessage(List<Album> albums) {
        return albums.stream().map(album -> album.getArtist().getName() + " - " + album.getTitle()).collect(Collectors.joining("\n"));
    }

    public void sendMessage(Long chatId, String message) {
        telegramClient.sendMessage(token, message, chatId);
    }
}
