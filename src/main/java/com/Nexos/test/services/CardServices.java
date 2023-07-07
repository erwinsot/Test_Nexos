package com.Nexos.test.services;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.Nexos.test.models.CardModel;
import com.Nexos.test.models.TransactionModel;
import com.Nexos.test.repositories.ICardRepository;
import com.Nexos.test.repositories.ITransactionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CardServices {

    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private ITransactionRepository transactionRepository;

    public String generateUniqueCardNumber(String productId) {
        String randomNumber = generateRandomNumber(); 
        String cardNumber = productId + randomNumber;
        Long idCard= Long.parseLong(cardNumber);
        //Long idCard= Long.parseLong(cardNumber);
        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaVencimiento = fechaCreacion.plusYears(3);              
        CardModel cardModel=new CardModel(idCard,"juan", "hernandez", fechaVencimiento, false, 0, false);
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
        Optional<CardModel> optionalEntity =cardRepository.findFirstByPrefix(idCard);

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

    public void rechargeCard (Map<String,String> request) {        
        long cardId = Long.parseLong(request.get("cardId"));        
        double balance = Double.parseDouble(request.get("balance"));
        Optional<CardModel> optionalEntity =cardRepository.findById(cardId);

        if (optionalEntity.isPresent()) {            
            CardModel cardModel = optionalEntity.get();
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


    public double getBalance (Long idCard) {        
        Optional<CardModel> optionalEntity =cardRepository.findFirstByPrefix(idCard);

        if (optionalEntity.isPresent()) {
            CardModel cardModel = optionalEntity.get();
            // Se activa la tarjeta en la base de datos
            return cardModel.getBalance();
            
        } else {
            throw new EntityNotFoundException("Tarjeta no encontrada");
        }
    }   
    
}
