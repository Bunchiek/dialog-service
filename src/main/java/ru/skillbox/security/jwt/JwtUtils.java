package ru.skillbox.security.jwt;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.client.AuthClient;

import java.text.ParseException;


@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${app.jwt.uriValidate}")
    private String uriValidate;

    @Value("${app.rabbitMQ.host}")
    private String rabbitMqHost;

    private final AuthClient authClient;

    public Boolean validateToken(String jwt) {
        String authorizationHeader = "Bearer " + jwt;
        return authClient.validateToken(authorizationHeader);
    }

    public static String getUsername(String jwtToken) {
        try {
            return SignedJWT.parse(jwtToken).getPayload().toJSONObject().get("id").toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
