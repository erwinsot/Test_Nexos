package com.Nexos.test.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Nexos.test.models.TransactionModel;
import com.Nexos.test.services.CardServices;
import com.Nexos.test.services.TransactionService;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping
public class CardController {

    @Autowired
    private CardServices cardServices;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/card/{productId}/number")
    public ResponseEntity<String> generateCardNumber(@PathVariable String productId) {
        // Lógica para generar el número de tarjeta
        String cardNumber = cardServices.generateUniqueCardNumber(productId);

        // Devuelve el número de tarjeta como respuesta
        return ResponseEntity.ok(cardNumber);
    } 

    @PostMapping("/card/enroll")
    public ResponseEntity<?> updateValue(@RequestBody  Map<String, String> request) {        
        try {
            String cardId = request.get("cardId");
            cardServices.activateCard(cardId);
            return ResponseEntity.ok("Tarjeta " + request.get("cardId") + " activada exitosamente");
        } catch (EntityNotFoundException ex) {
            String errorMessage = "Tarjeta no encontrada";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<String> blockCard(@PathVariable("cardId") Long cardId) {
        try {
            cardServices.blockCard(cardId);           
            return ResponseEntity.ok("Tarjeta bloqueada exitosamente");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al bloquear la tarjeta");
        }
    }

     @PostMapping("/card/balance")
    public ResponseEntity<String> rechargeBalance(@RequestBody Map<String, String> request) {
        try {
            cardServices.rechargeCard(request);            
            return ResponseEntity.ok("Saldo recargado exitosamente");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recargar el saldo");
        }
    }


    @GetMapping("/card/balance/{cardId}")
    public ResponseEntity<String> checkBalance(@PathVariable("cardId") String cardId) {
        try {
            
            double saldo = cardServices.getBalance(Long.parseLong(cardId));
            
            return ResponseEntity.ok("El saldo de la tarjeta " + cardId + " es: " + saldo);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar el saldo");
        }
    }

     @PostMapping("/transaction/purchase")
    public ResponseEntity<String> purchaseTransaction(@RequestBody Map<String, Object> request) {
        try {
            transactionService.purchaseTransaction(request);
            //cardServices.purchaseTransaction(request);
            //cardServices.hacernada(request);
            return ResponseEntity.ok("Transacción de compra exitosa");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarjeta no encontrada");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la transacción");
        }
    }

    
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionModel> getTransactionById(@PathVariable Long transactionId) {
        TransactionModel transaction =  transactionService.getTransactionById(transactionId);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }


    @PostMapping("/transaction/anulation")
    public ResponseEntity<String> cancelTransaction(@RequestBody Map<String, String> request) {
        try {
            transactionService.cancelTransaction(request);
            //cardServices.cancelTransaction(request);
            //cardServices.hacernada(request);
            return ResponseEntity.ok("Cancelacion exitosa");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaccion no encontrada");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la cancelacion de la transacción");
        }
    }   
    
}
