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
    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getShippingById(@PathVariable Long id) {
        Shipping shipping = shippingService.getShippingById(id);
        if (shipping == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipping);
    }

    // POST /shippings - crea una nuova spedizione

    @PostMapping
    public ResponseEntity<Shipping> createShipping(@RequestBody Shipping newShipping) {
        Shipping savedShipping = shippingService.createShipping(newShipping);
        if (savedShipping == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedShipping);
    }

    // PUT /shippings/{id} - aggiorna una spedizione esistente
    @PutMapping("/{id}")
    public ResponseEntity<Shipping> updateShipping(
            @PathVariable Long id,
            @RequestBody Shipping updatedShipping) {

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(@PathVariable Long id) {
        Shipping existingShipping = shippingService.getShippingById(id);
        if (existingShipping == null) {
            return ResponseEntity.notFound().build();
        }
        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build(); // 204 no content
    }
}
