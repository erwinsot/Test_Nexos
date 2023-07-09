package com.Nexos.test.services;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.Nexos.test.models.CardModel;
import com.Nexos.test.repositories.ICardRepository;
import com.Nexos.test.repositories.ITransactionRepository;

import jakarta.persistence.EntityNotFoundException;

public class CardServicesTest {

    @Mock
    private ITransactionRepository transactionRepository;

    @Mock
    private ICardRepository cardRepository;

    @InjectMocks
    private CardServices cardServices;

    @InjectMocks
    private CardServices transactionServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBlockCard_ExistingCard() {
        // Arrange
        Long idCard = 123456L;
        CardModel cardModel = new CardModel(idCard, "juan", "hernandez", LocalDate.now(), true, 0, false);
        Optional<CardModel> optionalEntity = Optional.of(cardModel);
        Mockito.when(cardRepository.findFirstByPrefix(idCard)).thenReturn(optionalEntity);

        // Act
        cardServices.blockCard(idCard);

        // Assert
        Mockito.verify(cardRepository, Mockito.times(1)).save(cardModel);
        Assertions.assertTrue(cardModel.isBlockedCard());
    }

    @Test
    public void testBlockCard_NonExistingCard() {
        // Arrange
        Long idCard = 123456L;
        Optional<CardModel> optionalEntity = Optional.empty();
        Mockito.when(cardRepository.findFirstByPrefix(idCard)).thenReturn(optionalEntity);

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            cardServices.blockCard(idCard);
        });
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any());
    }


    @Test
    public void testRechargeCard_ExistingCard() {
        // Arrange
        long cardId = 123456L;
        double currentBalance = 100.0;
        double rechargeAmount = 50.0;
        double expectedNewBalance = currentBalance + rechargeAmount;

        CardModel cardModel = new CardModel(cardId, "juan", "hernandez", LocalDate.now(), true, currentBalance, false);
        Optional<CardModel> optionalEntity = Optional.of(cardModel);
        Mockito.when(cardRepository.findById(cardId)).thenReturn(optionalEntity);

        Map<String, String> request = new HashMap<>();
        request.put("cardId", String.valueOf(cardId));
        request.put("balance", String.valueOf(rechargeAmount));

        // Act
        cardServices.rechargeCard(request);

        // Assert
        Mockito.verify(cardRepository, Mockito.times(1)).save(cardModel);
        Assertions.assertEquals(expectedNewBalance, cardModel.getBalance());
    }

    @Test
    public void testRechargeCard_NonExistingCard() {
        // Arrange
        long cardId = 123456L;
        double rechargeAmount = 50.0;

        Optional<CardModel> optionalEntity = Optional.empty();
        Mockito.when(cardRepository.findById(cardId)).thenReturn(optionalEntity);

        Map<String, String> request = new HashMap<>();
        request.put("cardId", String.valueOf(cardId));
        request.put("balance", String.valueOf(rechargeAmount));

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            cardServices.rechargeCard(request);
        });
        Mockito.verify(cardRepository, Mockito.never()).save(Mockito.any());
    }
}
    