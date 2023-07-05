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

import com.Nexos.test.services.CardServices;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping
public class CardController {

    @Autowired
    private CardServices cardServices;

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

    
    
}
