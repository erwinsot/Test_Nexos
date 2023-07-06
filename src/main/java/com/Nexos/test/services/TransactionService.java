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
public class TransactionService {

    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private ITransactionRepository transactionRepository;


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
        long cardId = Long.parseLong(request.get("cardId"));
        Optional<TransactionModel> optionalTransaction = transactionRepository.findById(transactionId);
        
        if (optionalTransaction.isPresent()) {
            TransactionModel transaction = optionalTransaction.get();
            
            // Verificar si la transacción ya está anulada
            if (transaction.isCacelled()) {                
                throw new IllegalArgumentException("La transacción ya está anulada");
            }            
            
            // Obtener la fecha actual
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime transactionDate=transaction.getTransactionDate();
            
            // Verificar si la transacción es válida para anulación (menor a 24 horas)
            if (transactionDate != null) {
                if (transaction.getTransactionDate().plusHours(24).isBefore(currentDateTime)) {
                throw new IllegalArgumentException("La transacción no es válida para anulación");
            }

            }else {
                throw new IllegalArgumentException("La fecha de transacción es nula");
            }
            
            //verificar si la transaccion corresponde a la tarjeta
            Long idCard=transaction.getCard().getCardId();
            if(idCard!=cardId ){
                throw new IllegalArgumentException("La transacción no corresponde con la tarjeta");
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
