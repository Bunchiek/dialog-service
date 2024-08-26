package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.dto.LoginResponse;
import ru.skillbox.dto.SetStatusMessageReadRs;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {


    @PostMapping ()
    public ResponseEntity<LoginResponse> successAuth() {
        LoginResponse response = LoginResponse.builder().data("ACCESS").timestamp(Instant.now().toEpochMilli()).build();
        return ResponseEntity.ok(response);
    }

}
