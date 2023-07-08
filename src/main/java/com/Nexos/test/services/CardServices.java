package com.Nexos.test.services;

import java.time.LocalDate;
import java.util.Map;
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
        long cardId = Long.parseLong(productId);
        Optional<CardModel> optionalEntity = cardRepository.findFirstByPrefix(cardId);
        if(!optionalEntity.isPresent()){
            String randomNumber = generateRandomNumber();
            String cardNumber = productId + randomNumber;
            Long idCard = Long.parseLong(cardNumber);
            LocalDate fechaCreacion = LocalDate.now();
            LocalDate fechaVencimiento = fechaCreacion.plusYears(3);
            CardModel cardModel = new CardModel(idCard, null, null, fechaVencimiento, false, 0, false);
            cardRepository.save(cardModel);
            return cardNumber;
        }
        else{
            throw new IllegalStateException("Ya se ha creado una tarjeta con este id");
        }
        
    }

    private String generateRandomNumber() {

        long random = Math.round(Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(random);
    }

    public void activateCard(String idCard) {
        long cardId = Long.parseLong(idCard);
        Optional<CardModel> optionalEntity = cardRepository.findById(cardId);

        if (optionalEntity.isPresent()) {
            CardModel cardModel = optionalEntity.get();
            // Se activa la tarjeta en la base de datos
            cardModel.setActivate(true);
            // Guarda los cambios en la base de datos
            cardRepository.save(cardModel);

        } else {
            throw new EntityNotFoundException("Tarjeta con id "+ idCard + " no encontrada");
        }
    }

    public void blockCard(Long idCard) {
        Optional<CardModel> optionalEntity = cardRepository.findFirstByPrefix(idCard);

        if (optionalEntity.isPresent()) {            
            CardModel cardModel = optionalEntity.get();
            if(cardModel.getBlockedCard()){
                throw new IllegalStateException("La tarjeta ya se encuentra bloqueada");
            }
            // Se activa la tarjeta en la base de datos
            cardModel.setBlockedCard(true);
            // Guarda los cambios en la base de datos
            cardRepository.save(cardModel);
        } else {
            throw new EntityNotFoundException("Tarjeta con id "+ idCard + " no encontrada");
        }
    }

    public void rechargeCard(Map<String, String> request) {
        long cardId = Long.parseLong(request.get("cardId"));
        double balance = Double.parseDouble(request.get("balance"));
        Optional<CardModel> optionalEntity = cardRepository.findById(cardId);

        if (optionalEntity.isPresent()) {
            CardModel cardModel = optionalEntity.get();
            if (!cardModel.isActivate()) {
                throw new IllegalStateException("La tarjeta no está activa");
            }
            // Obtener el saldo actual de la tarjeta
            double currentBalance = cardModel.getBalance();

            // Calcular el nuevo saldo sumando el saldo presente al saldo actual
            double newBalance = currentBalance + balance;
            cardModel.setBalance(newBalance);
            // Guarda los cambios en la base de datos
            cardRepository.save(cardModel);
        } else {
            throw new EntityNotFoundException("Tarjeta no encontrada");
        }
    }

    public double getBalance(Long idCard) {
        Optional<CardModel> optionalEntity = cardRepository.findFirstByPrefix(idCard);

        if (optionalEntity.isPresent()) {
            CardModel cardModel = optionalEntity.get();
            // Se activa la tarjeta en la base de datos
            return cardModel.getBalance();

        } else {
            throw new EntityNotFoundException("Tarjeta no encontrada");
        }
    }

}
