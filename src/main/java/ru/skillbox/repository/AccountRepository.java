package ru.skillbox.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Status;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    @EntityGraph(attributePaths = {"dialogs"})
    Optional<Account> findById(UUID id);


    @Query("SELECT m FROM Account m WHERE m.id = :id")
    Optional<Account> findByUUID(@Param("id") String id);
}
