package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.entity.Role;
import ru.skillbox.entity.StatusCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private Long id;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String token;
    private StatusCode statusCode;
    private String firstName;
    private String lastName;
    private LocalDateTime regDate;
    private LocalDateTime birthDate;
    private String messagePermission;
    private LocalDateTime lastOnlineTime;
    private boolean isOnline;
    private boolean isBlocked;
    private boolean isDeleted;
    private String photoId;
    private String photoName;
    private Role role;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String password;

}
