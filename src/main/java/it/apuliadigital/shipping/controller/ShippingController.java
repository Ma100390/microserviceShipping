package it.apuliadigital.shipping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.apuliadigital.shipping.models.Shipping;
import it.apuliadigital.shipping.service.IShipping;

@RestController
@RequestMapping("/shippings") // prefisso comune per tutti gli endpoint
public class ShippingController {

    @Autowired
    private IShipping shippingService;

    // GET /shippings - ottieni tutti gli spedizioni
    // Se non ci sono spedizioni, rispondi con 204 No Content Se ci sono, ritorni la
    // lista con 200 OK.
    @Operation(summary = "Ottieni tutte le spedizioni", description = "Restituisce una lista di tutte le spedizioni registrate.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista delle spedizioni trovata"),
            @ApiResponse(responseCode = "204", description = "Nessuna spedizione trovata"),
    })
    @GetMapping
    public ResponseEntity<List<Shipping>> getShippings() {
        List<Shipping> shippings = shippingService.getAllShippings();
        if (shippings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shippings);
    }

    // GET /shippings/{id} - ottieni una spedizione per id
    // Cerca la spedizione per id. Se non trovata, 404 Not Found.Se trovata, ritorna
    // 200 OK con l’oggetto.
    @Operation(summary = "Ottieni spedizione per ID", description = "Ricerca una spedizione tramite l'ID specificato.", responses = {
            @ApiResponse(responseCode = "200", description = "Spedizione trovata"),
            @ApiResponse(responseCode = "404", description = "Spedizione non trovata")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getShippingById(
            @Parameter(description = "ID della spedizione da recuperare") @PathVariable Long id) {
        Shipping shipping = shippingService.getShippingById(id);
        if (shipping == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipping);
    }

    // POST /shippings - crea una nuova spedizione

    @Operation(summary = "Crea una nuova spedizione", description = "Inserisce una nuova spedizione nel sistema.", responses = {
            @ApiResponse(responseCode = "200", description = "Spedizione creata con successo"),
            @ApiResponse(responseCode = "400", description = "Dati di input non validi")
    })
    @PostMapping
    public ResponseEntity<Shipping> createShipping(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dati della nuova spedizione", required = true, content = @Content(schema = @Schema(implementation = Shipping.class))) @RequestBody Shipping newShipping) {
        Shipping savedShipping = shippingService.createShipping(newShipping);
        if (savedShipping == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedShipping);
    }

    // PUT /shippings/{id} - aggiorna una spedizione esistente
    @Operation(summary = "Aggiorna una spedizione", description = "Modifica le informazioni di una spedizione esistente.", responses = {
            @ApiResponse(responseCode = "200", description = "Spedizione aggiornata"),
            @ApiResponse(responseCode = "404", description = "Spedizione non trovata"),
            @ApiResponse(responseCode = "400", description = "Dati di input non validi")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Shipping> updateShipping(
            @Parameter(description = "ID della spedizione da aggiornare") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dati aggiornati della spedizione", required = true, content = @Content(schema = @Schema(implementation = Shipping.class))) @RequestBody Shipping updatedShipping) {

        Shipping existingShipping = shippingService.getShippingById(id);
        if (existingShipping == null) {
            return ResponseEntity.notFound().build();
        }

        // Aggiorno i campi
        existingShipping.setStatus(updatedShipping.getStatus());
        existingShipping.setDestinationAddress(updatedShipping.getDestinationAddress());
        existingShipping.setCourier(updatedShipping.getCourier());
        existingShipping.setOrderCode(updatedShipping.getOrderCode());

        Shipping savedShipping = shippingService.updateShipping(existingShipping);
        if (savedShipping == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedShipping);
    }

    // DELETE /shippings/{id} - elimina una spedizione
    @Operation(summary = "Elimina una spedizione", description = "Elimina una spedizione esistente tramite ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Spedizione eliminata"),
            @ApiResponse(responseCode = "404", description = "Spedizione non trovata")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(
            @Parameter(description = "ID della spedizione da eliminare") @PathVariable Long id) {
        Shipping existingShipping = shippingService.getShippingById(id);
        if (existingShipping == null) {
            return ResponseEntity.notFound().build();
        }
        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build(); // 204 no content
    }
}
