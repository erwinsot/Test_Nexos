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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Date;
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

    CardModel cardModel = Mockito.mock(CardModel.class);    

    TransactionModel transactionModel = new TransactionModel();
    transactionModel.setTransactionId(transactionId);
    transactionModel.setTransactionDate(LocalDateTime.now());
    transactionModel.setCacelled(false);
    transactionModel.setCard(cardModel);   
    
    cardModel.setCardId(cardId);

    Optional<TransactionModel> optionalTransaction = Optional.of(transactionModel);
    Mockito.when(transactionRepository.findById(transactionId)).thenReturn(optionalTransaction);
    Mockito.when(transactionRepository.save(transactionModel)).thenReturn(transactionModel);
    Mockito.when(cardModel.getCardId()).thenReturn(cardId);
    
    

    Mockito.when(cardRepository.findById(cardId)).thenReturn(Optional.of(cardModel));
    Mockito.when(cardRepository.save(cardModel)).thenReturn(cardModel);
    transactionModel.setCard(cardModel);

    Map<String, String> request = new HashMap<>();
    request.put("transactionId", String.valueOf(transactionId));
    request.put("cardId", String.valueOf(cardId));
    

    // Act
    transactionServices.cancelTransaction(request);

    // Assert
    Assertions.assertTrue(transactionModel.isCacelled());
    Assertions.assertEquals(0.0, cardModel.getBalance());
}


    @Test
    void cancelTransaction_ValidTransaction_Success() {
    // Arrange
    Map<String, String> request = Map.of("transactionId", "123", "cardId", "456");

    TransactionModel transaction = new TransactionModel();
    transaction.setTransactionId(123L);
    transaction.setCacelled(false);
    transaction.setTransactionDate(LocalDateTime.now().minusHours(12)); // Within the 24-hour window

    CardModel card = new CardModel();
    card.setCardId(456L);
    card.setBalance(1000);

    // Simulate the relationship between TransactionModel and CardModel
    transaction.setCard(card);

    when(transactionRepository.findById(123L)).thenReturn(Optional.of(transaction));
    when(cardRepository.findById(456L)).thenReturn(Optional.of(card));

    // Act
    transactionServices.cancelTransaction(request);

    // Assert
    verify(transactionRepository, times(1)).findById(123L);    
    verify(cardRepository, times(1)).save(eq(card)); // Verify that the correct card is saved

    
}



}
    