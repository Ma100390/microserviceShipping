package it.apuliadigital.shipping.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.apuliadigital.shipping.models.Shipping;
import it.apuliadigital.shipping.service.IShipping;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/shippings")
public class ShippingController {

    @Autowired
    private IShipping shippingService;

    @Operation(summary = "Ottieni tutte le spedizioni", description = "Restituisce una lista di tutte le spedizioni registrate.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista delle spedizioni trovata"),
            @ApiResponse(responseCode = "204", description = "Nessuna spedizione trovata")
    })
    @GetMapping
    public ResponseEntity<List<Shipping>> getShippings() {
        List<Shipping> shippings = shippingService.getAllShippings();
        return shippings.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(shippings);
    }

    @Operation(summary = "Ottieni spedizione per ID", description = "Ricerca una spedizione tramite l'ID specificato.", responses = {
            @ApiResponse(responseCode = "200", description = "Spedizione trovata"),
            @ApiResponse(responseCode = "404", description = "Spedizione non trovata")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getShippingById(
            @Parameter(description = "ID della spedizione da recuperare") @PathVariable Long id) {
        return Optional.ofNullable(shippingService.getShippingById(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crea una nuova spedizione", description = "Inserisce una nuova spedizione nel sistema.", responses = {
            @ApiResponse(responseCode = "200", description = "Spedizione creata con successo"),
            @ApiResponse(responseCode = "400", description = "Dati di input non validi")
    })
    public ResponseEntity<Shipping> createShipping(@RequestBody @Valid Shipping newShipping) {
        return Optional.ofNullable(shippingService.createShipping(newShipping))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna una spedizione", description = "Modifica le informazioni di una spedizione esistente.", responses = {
            @ApiResponse(responseCode = "200", description = "Spedizione aggiornata"),
            @ApiResponse(responseCode = "404", description = "Spedizione non trovata"),
            @ApiResponse(responseCode = "400", description = "Dati di input non validi")
    })
    public ResponseEntity<Shipping> updateShipping(
            @Parameter(description = "ID della spedizione da aggiornare") @PathVariable Long id,
            @RequestBody @Valid Shipping updatedShipping) {
        Shipping existing = shippingService.getShippingById(id);
        if (existing == null)
            return ResponseEntity.notFound().build();

        existing.setOrderCode(updatedShipping.getOrderCode());
        existing.setStatus(updatedShipping.getStatus());
        existing.setDestinationAddress(updatedShipping.getDestinationAddress());
        existing.setCourier(updatedShipping.getCourier());

        return Optional.ofNullable(shippingService.updateShipping(existing))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Elimina una spedizione", description = "Elimina una spedizione esistente tramite ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Spedizione eliminata"),
            @ApiResponse(responseCode = "404", description = "Spedizione non trovata")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(
            @Parameter(description = "ID della spedizione da eliminare") @PathVariable Long id) {
        if (shippingService.getShippingById(id) == null)
            return ResponseEntity.notFound().build();

        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build();
    }
}
