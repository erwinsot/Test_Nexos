package com.Nexos.test.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nexos.test.models.CardModel;
import com.Nexos.test.repositories.ICardRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CardServices {

    @Autowired
    private ICardRepository cardRepository;

    public String generateUniqueCardNumber(String productId) {
        String randomNumber = generateRandomNumber();
        String cardNumber = productId + randomNumber;
        Long idCard= Long.parseLong(cardNumber);
        CardModel cardModel=new CardModel(idCard, "juan", "hernandez", null, false, 0, false, 0);
        cardRepository.save(cardModel);
        return cardNumber;
    }

    private String generateRandomNumber() {
       
        long random = Math.round(Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(random);
    }

    public void activateCard(String idCard) {
        long cardId = Long.parseLong(idCard);
        Optional<CardModel> optionalEntity =cardRepository.findById(cardId);

        if (optionalEntity.isPresent()) {
            CardModel cardModel = optionalEntity.get();
            // Se activa la tarjeta en la base de datos
            cardModel.setActivate(true);
            // Guarda los cambios en la base de datos
            cardRepository.save(cardModel);
            
        } else {
            throw new EntityNotFoundException("Tarjeta no encontrada");
        }
    }

    public void blockCard(Long idCard) {
        Optional<CardModel> optionalEntity =cardRepository.findById(idCard);

        if (optionalEntity.isPresent()) {
            CardModel cardModel = optionalEntity.get();
            // Se activa la tarjeta en la base de datos
            cardModel.setBlockedCard(true);
            // Guarda los cambios en la base de datos
            cardRepository.save(cardModel);
        } else {
            throw new EntityNotFoundException("Tarjeta no encontrada");
        }
    }
    
}
