package com.team8.kanban.domain.card.repository;

import com.team8.kanban.domain.card.entity.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {
    @Query(value = "SELECT c FROM CardUser c WHERE c.user.id = :userId AND c.card.cardId = :cardId")
    CardUser findByUserInCard(Long userId, Long cardId);
}