package ru.skillbox.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Base64;


@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.uriValidate}")
    private static String uriValidate;





    public static Boolean validateToken(String jwt) throws IOException, InterruptedException, java.io.IOException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriValidate))
                .header("Authorization", "Bearer " + jwt)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return true;
        } else {
            throw new RuntimeException("Failed to validate token: " + response.statusCode());
        }
    }

    public static String getUsername(String jwtToken) {
        String[] jwtParts = jwtToken.split("\\.");
        String payload = jwtParts[1];

        byte[] decodedBytes = Base64.getDecoder().decode(payload);
        return new String(decodedBytes);
    }
}
