package ru.skillbox.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "dialogs")
@NoArgsConstructor
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unread_count", nullable = false)
    private Long unreadCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_partner_id", nullable = false)
    private Account conversationPartner;

    @OneToMany(mappedBy = "dialog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

}