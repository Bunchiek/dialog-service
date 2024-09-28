package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "dialogs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unread_count", nullable = false)
    private Long unreadCount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "participant_one_id", nullable = false)
//    private Account participantOne;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "participant_two_id", nullable = false)
//    private Account participantTwo;

    @Column(name = "participant_one_id", nullable = false)
    private UUID participantOne;
    @Column(name = "participant_two_id", nullable = false)
    private UUID participantTwo;

    @OneToMany(mappedBy = "dialog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

}