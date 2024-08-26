package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    @Column(name = "about")
    private String about;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code", nullable = false)
    private StatusCode statusCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "message_permission")
    private String messagePermission;

    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "photo_id")
    private String photoId;

    @Column(name = "photo_name")
    private String photoName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "conversationPartner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dialog> dialogs;


}
