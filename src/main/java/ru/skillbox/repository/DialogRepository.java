package ru.skillbox.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {


    Optional<Dialog> findByConversationPartner(Account conversationPartner);


    @EntityGraph(attributePaths = {"messages"})
    List<Dialog> findAll();
}
