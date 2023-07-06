package com.Nexos.test.services;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.Nexos.test.models.CardModel;
import com.Nexos.test.models.TransactionModel;
import com.Nexos.test.repositories.ICardRepository;
import com.Nexos.test.repositories.ITransactionRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

  
    
public class TransactionServiceTest {


    @Mock
    private ITransactionRepository transactionRepository;

    @Mock
    private ICardRepository cardRepository;

    @InjectMocks
    private CardServices cardServices;

    @InjectMocks
    private TransactionService transactionServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    
        
    @Test
    public void testCancelTransaction_CancelledTransaction() {
        // Arrange
        long transactionId = 123456L;
        long cardId = 7890L;

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(transactionId);
        transactionModel.setCacelled(true);

        Optional<TransactionModel> optionalTransaction = Optional.of(transactionModel);
        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(optionalTransaction);

        Map<String, String> request = new HashMap<>();
        request.put("transactionId", String.valueOf(transactionId));
        request.put("cardId", String.valueOf(cardId));

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionServices.cancelTransaction(request);
        });
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testCancelTransaction_InvalidTransactionDate() {
        // Arrange
        long transactionId = 123456L;
        long cardId = 7890L;

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(transactionId);
        transactionModel.setCacelled(false);
        transactionModel.setTransactionDate(LocalDateTime.now().minusHours(25));

        Optional<TransactionModel> optionalTransaction = Optional.of(transactionModel);
        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(optionalTransaction);

        Map<String, String> request = new HashMap<>();
        request.put("transactionId", String.valueOf(transactionId));
        request.put("cardId", String.valueOf(cardId));

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionServices.cancelTransaction(request);
        });
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any());
    }


    @Test
    public void testCancelTransaction_NonExistingTransaction() {
        // Arrange
        long transactionId = 123456L;
        long cardId = 7890L;

        Optional<TransactionModel> optionalTransaction = Optional.empty();
        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(optionalTransaction);

        Map<String, String> request = new HashMap<>();
        request.put("transactionId", String.valueOf(transactionId));
        request.put("cardId", String.valueOf(cardId));

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            transactionServices.cancelTransaction(request);
        });
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any());
    }


    @Test
    public void testCancelTransaction_ValidTransaction() {
    // Arrange
    long transactionId = 123456L;
    long cardId = 7890L;

    TransactionModel transactionModel = new TransactionModel();
    transactionModel.setTransactionId(transactionId);
    transactionModel.setCacelled(false);

    CardModel cardModel = new CardModel();
    cardModel.setCardId(cardId);

    Optional<TransactionModel> optionalTransaction = Optional.of(transactionModel);
    Mockito.when(transactionRepository.findById(transactionId)).thenReturn(optionalTransaction);
    Mockito.when(transactionRepository.save(transactionModel)).thenReturn(transactionModel);

    Mockito.when(cardRepository.findById(cardId)).thenReturn(Optional.of(cardModel));
    Mockito.when(cardRepository.save(cardModel)).thenReturn(cardModel);

    Map<String, String> request = new HashMap<>();
    request.put("transactionId", String.valueOf(transactionId));
    request.put("cardId", String.valueOf(cardId));

    // Act
    transactionServices.cancelTransaction(request);

    // Assert
    Assertions.assertTrue(transactionModel.isCacelled());
    Assertions.assertEquals(0.0, cardModel.getBalance());
}

}
    