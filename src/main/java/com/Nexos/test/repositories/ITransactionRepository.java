package com.Nexos.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nexos.test.models.TransactionModel;

public interface ITransactionRepository extends JpaRepository<TransactionModel, Long >{
    
}
