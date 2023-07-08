package com.Nexos.test.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nexos.test.models.TransactionModel;
import com.Nexos.test.services.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping
public class TransactionController {
    @Autowired
    private TransactionService transactionService;    

    @PostMapping("/transaction/purchase")
    @Operation(summary = "Verifica el saldo de una tarjeta", description = "Verifica el saldo de una tarjeta utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo consultado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al consultar el saldo") })
    public ResponseEntity<String> purchaseTransaction(@RequestBody Map<String, Object> request) {

        try {
            TransactionModel transactionModel= transactionService.purchaseTransaction(request);
            return ResponseEntity.ok("Transacción de compra exitosa, id de transaccion "+ transactionModel.getTransactionId());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarjeta no encontrada");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la transacción");
        }
    }

    @Operation(summary = "Obtener una transacción por ID", description = "Devuelve una transacción específica basada en el ID proporcionado")
    @ApiResponse(responseCode = "200", description = "Transacción encontrada")
    @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        if (transactionId == null || transactionId < 0) {
            return ResponseEntity.badRequest().body("El id de la transaccion debe ser un valor valido");
        }
        TransactionModel transaction = transactionService.getTransactionById(transactionId);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transaction/anulation")
    @Operation(summary = "Cancelar transacción", description = "Cancela una transacción utilizando los datos proporcionados en la solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancelación exitosa"),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error al procesar la cancelación de la transacción") })
    public ResponseEntity<String> cancelTransaction(@RequestBody Map<String, String> request) {
        String transactionIdStr = request.get("transactionId");
        String cardId = request.get("cardId");
        if (transactionIdStr == null || transactionIdStr.isEmpty()) {
            return ResponseEntity.badRequest().body("El id de la transaccion es obligatorio");
        }
        if (cardId == null || cardId.length() != 16 || !cardId.matches("\\d+")) {
            return ResponseEntity.badRequest().body("La tarjeta debe tener exactamente 16 dígitos numéricos");
        }

        try {
            transactionService.cancelTransaction(request);
            return ResponseEntity.ok("Cancelacion exitosa");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaccion no encontrada");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


    
    
}
