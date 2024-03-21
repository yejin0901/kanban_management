package com.team8.kanban.domain.card.repository;


import com.team8.kanban.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

}