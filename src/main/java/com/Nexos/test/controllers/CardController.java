package com.Nexos.test.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nexos.test.models.CardModel;
import com.Nexos.test.services.CardServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping()
@CrossOrigin("*")
public class CardController {

    @Autowired
    private CardServices cardServices;

    

    @GetMapping("/card/{productId}/number")
    @Operation(summary = "Genera número de tarjeta a partir de una id de 6 dígitos", description = "Se genera un número de tarjeta válido a partir de un id de 6 dígitos. La tarjeta tendrá 16 dígitos una vez creada.")
    @ApiResponse(responseCode = "200", description = "Se ingresa un número de 6 dígitos para que devuelva correctamente")
    public ResponseEntity <?> generateCardNumber(@PathVariable String productId) {
        // Validación de longitud del productId
        if (productId.length() != 6) {
            return ResponseEntity.badRequest().body("El Id del producto debe ser de exactamente 6 Dígitos");
        }
        try {
            // Lógica para generar el número de tarjeta
            CardModel cardNumber = cardServices.generateUniqueCardNumber(productId);

            // Devuelve el número de tarjeta como respuesta
            return ResponseEntity.ok(cardNumber );     
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        
    }
 

    @PostMapping("/card/enroll")
    @Operation(summary = "Activa una tarjeta", description = "Activa una tarjeta utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada ejemplo  {'cardId': '1020301234567801'} Tarjeta activada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    public ResponseEntity<?> cardActivate(@RequestBody Map<String, String> request) {
        String cardId = request.get("cardId");

        // Validación del campo cardId
        if (cardId == null || cardId.length() != 16 || !cardId.matches("\\d+")) {
            return ResponseEntity.badRequest().body("La tarjeta debe tener exactamente 16 dígitos numéricos");
        }

        try {
            cardServices.activateCard(cardId);
            return ResponseEntity.ok("Tarjeta " + cardId + " activada exitosamente");
        } catch (EntityNotFoundException ex) {
            String errorMessage = "Tarjeta no encontrada";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/card/{cardId}")
    @Operation(summary = "Bloquea una tarjeta", description = "Bloquea una tarjeta utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta bloqueada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al bloquear la tarjeta") })    
    public ResponseEntity<String> blockCard(@PathVariable("cardId") Long cardId) {
        if (cardId.toString().length() != 6) {
            return ResponseEntity.badRequest().body("El Id del producto debe ser de exactamente 6 dígitos");
        }
        try {
            cardServices.blockCard(cardId);
            return ResponseEntity.ok("Tarjeta bloqueada exitosamente");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al bloquear la tarjeta");
        }
    }

    @PostMapping("/card/balance")
    @Operation(summary = "Recarga el saldo de una tarjeta", description = "Recarga el saldo de una tarjeta utilizando los datos proporcionados en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada Ejemplo {'cardId': '1020301234567801', 'balance': '10000'}  Saldo recargado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al recargar el saldo") })
    public ResponseEntity<String> rechargeBalance(@RequestBody Map<String, String> request) {
        String cardId = request.get("cardId");
        String amount = request.get("balance");

        if (amount == null || amount.isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'balance' es obligatorio");
        }
        if (cardId == null || cardId.length() != 16 || !cardId.matches("\\d+")) {
            return ResponseEntity.badRequest().body("La tarjeta debe tener exactamente 16 dígitos numéricos");
        }
        try {
            cardServices.rechargeCard(request);
            return ResponseEntity.ok("Saldo recargado exitosamente");        
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recargar el saldo");
        }
    }

    @GetMapping("/card/balance/{cardId}")
    @Operation(summary = "Verificar saldo de tarjeta", description = "Verifica el saldo de una tarjeta utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo consultado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al consultar el saldo") })
    public ResponseEntity<String> checkBalance(@PathVariable("cardId") String cardId) {
        if (cardId.length() != 6) {
            return ResponseEntity.badRequest().body("El Id del producto debe ser de exactamente 6 Dígitos");
        }

        try {

            double saldo = cardServices.getBalance(Long.parseLong(cardId));

            return ResponseEntity.ok("El saldo de la tarjeta " + cardId + " es: " + saldo);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar el saldo");
        }
    }

    
}
