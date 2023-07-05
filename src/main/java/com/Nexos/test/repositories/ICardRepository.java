package com.Nexos.test.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Nexos.test.models.CardModel;

public interface ICardRepository extends JpaRepository <CardModel, Long>{

    @Query("SELECT c FROM CardModel c WHERE CAST(c.cardId AS Long) LIKE CONCAT(:prefix, '%')")
    Optional<CardModel> findFirstByPrefix(@Param("prefix") Long prefix);
    
}
