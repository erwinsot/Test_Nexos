package com.Nexos.test.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        System.out.println(cardId);
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

    public void purchaseTransaction(Map<String, Object> request) {
        // Buscar la tarjeta correspondiente al número de tarjeta
        String cardIdString = (String) request.get("cardId");
        Long cardId = Long.parseLong(cardIdString);
        String priceValue=request.get("price").toString();
        double price = Double.parseDouble(priceValue);    
              
        Optional<CardModel> optionalEntity =cardRepository.findById(cardId);
        CardModel card = optionalEntity.get();        

        // Verificar las condiciones para la transacción
        if (card == null) {
            throw new EntityNotFoundException("Tarjeta no encontrada");
        }

        if (!card.isActivate()) {
            throw new IllegalStateException("La tarjeta no está activa");
        }

        if (card.isBlockedCard()) {
            throw new IllegalStateException("La tarjeta está bloqueada");
        }

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(card.getExpirationDate())) {
            throw new IllegalStateException("La tarjeta está vencida");
        }

        if (card.getBalance() < price) {
            throw new IllegalStateException("Saldo insuficiente en la tarjeta");
        }

        // Realizar la transacción
        TransactionModel transaction = new TransactionModel();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCacelled(false);
        transaction.setCard(card);
        transaction.setAmount(price);

        // Actualizar el saldo de la tarjeta
        card.setBalance(card.getBalance() - price);

        // Guardar la transacción y actualizar la tarjeta en la base de datos
        transactionRepository.save(transaction);
        cardRepository.save(card);
    }

    public TransactionModel getTransactionById(Long transactionId) {
        Optional<TransactionModel> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.orElse(null);
    }



    public void cancelTransaction(@RequestBody Map<String, String> request) {
        long transactionId = Long.parseLong(request.get("transactionId"));
        Optional<TransactionModel> optionalTransaction = transactionRepository.findById(transactionId);
        
        if (optionalTransaction.isPresent()) {
            TransactionModel transaction = optionalTransaction.get();
            
            // Verificar si la transacción ya está anulada
            if (transaction.isCacelled()) {
                throw new IllegalArgumentException("La transacción ya está anulada");
            }
            
            // Obtener la fecha actual
            LocalDateTime currentDateTime = LocalDateTime.now();
            
            // Verificar si la transacción es válida para anulación (menor a 24 horas)
            if (transaction.getTransactionDate().plusHours(24).isBefore(currentDateTime)) {
                throw new IllegalArgumentException("La transacción no es válida para anulación");
            }
            
            // Obtener la tarjeta asociada a la transacción
            CardModel card = transaction.getCard();
            
            // Actualizar el saldo de la tarjeta sumando el valor de la compra anulada
            card.setBalance(card.getBalance() + transaction.getAmount());
            
            // Marcar la transacción como anulada
            transaction.setCacelled(true);
            
            // Guardar los cambios en la base de datos
            cardRepository.save(card);
            transactionRepository.save(transaction);
        } else {
            throw new EntityNotFoundException("Transacción no encontrada");
        }
    }
    

    
    
}
