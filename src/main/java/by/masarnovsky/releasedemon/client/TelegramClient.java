package by.masarnovsky.releasedemon.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "telegramclient", url = "https://api.telegram.org")
public interface TelegramClient {

    @RequestMapping(method = RequestMethod.POST, value = "/bot{token}/sendMessage")
    void sendMessage(@PathVariable String token, @RequestParam String text, @RequestParam Long chat_id);
}