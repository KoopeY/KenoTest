package ru.koopey.test_keno.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.koopey.test_keno.model.KenoGame;

public class RequestUtils {
    private static RestTemplate restTemplate = new RestTemplate();

    public static KenoGame getGameState() {
        ResponseEntity<KenoGame> response = restTemplate.getForEntity(
                "https://abdur.cc/kenodata2/v2/99908",
                KenoGame.class);

        return response.getBody();
    }
}
