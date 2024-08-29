package ru.skillbox.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    @Override
    @EntityGraph(attributePaths = {"dialogs"})
    Optional<Account> findById(Long aLong);
}
