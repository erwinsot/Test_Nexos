package com.Nexos.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nexos.test.models.CardModel;

public interface ICardRepository extends JpaRepository <CardModel, Long>{
    
}
