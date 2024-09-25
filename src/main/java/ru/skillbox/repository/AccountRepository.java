package ru.skillbox.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findById(UUID id);

    @Query("SELECT m FROM Account m WHERE m.id = :id")
    Optional<Account> findByUUID(@Param("id") UUID id);

    @Query("SELECT DISTINCT a FROM Account a " +
            "LEFT JOIN FETCH a.dialogsAsParticipantOne d1 " +
            "LEFT JOIN FETCH d1.messages " +
            "LEFT JOIN FETCH d1.participantOne " +
            "LEFT JOIN FETCH d1.participantTwo " +
            "LEFT JOIN FETCH a.dialogsAsParticipantTwo d2 " +
            "LEFT JOIN FETCH d2.messages " +
            "LEFT JOIN FETCH d2.participantOne " +
            "LEFT JOIN FETCH d2.participantTwo " +
            "WHERE a.id = :id")
    Optional<Account> findByIdWithDialogsAndMessages(@Param("id") UUID id);


}
