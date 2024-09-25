package ru.skillbox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {


    @Query("SELECT d FROM Dialog d WHERE (d.participantOne.id = :participantOneId AND d.participantTwo.id = :participantTwoId) " +
            "OR (d.participantOne.id = :participantTwoId AND d.participantTwo.id = :participantOneId)")
    Optional<Dialog> findByParticipants(@Param("participantOneId") UUID participantOneId,
                                        @Param("participantTwoId") UUID participantTwoId);


}
