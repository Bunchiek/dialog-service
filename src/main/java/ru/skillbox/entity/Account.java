package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "participantOne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Dialog> dialogsAsParticipantOne = new HashSet<>();

    @OneToMany(mappedBy = "participantTwo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Dialog> dialogsAsParticipantTwo = new HashSet<>();

    public Set<Dialog> getAllDialogs() {
        Set<Dialog> allDialogs = new HashSet<>(dialogsAsParticipantOne);
        allDialogs.addAll(dialogsAsParticipantTwo);
        return allDialogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
