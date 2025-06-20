package it.apuliadigital.shipping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.apuliadigital.shipping.models.Shipping;
import it.apuliadigital.shipping.repository.ShippingRepository;

@Service
public class ShippingService implements IShipping {

    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public List<Shipping> getAllShippings() {
        return (List<Shipping>) shippingRepository.findAll();
    }

    @Override
    public Shipping getShippingById(Long id) {
        Optional<Shipping> optionalShipping = shippingRepository.findById(id);
        return optionalShipping.orElse(null);
    }

    @Override
    public Shipping createShipping(Shipping shipping) {
        if (shipping == null) {
            return null;
        }
        return shippingRepository.save(shipping);
    }

    @Override
    public Shipping updateShipping(Shipping shipping) {
        if (shipping == null || shipping.getId() == null) {
            return null; // ID mancante o oggetto nullo, non si può aggiornare
        }

        boolean exists = shippingRepository.existsById(shipping.getId());
        if (!exists) {
            return null; // Entità con questo ID non esiste, non aggiorniamo
        }

        // Se l'entità esiste, aggiorna salvando
        return shippingRepository.save(shipping);
    }

    @Override
    public void deleteShipping(Long id) {
        // Verifica che esista prima di cancellare
        if (shippingRepository.existsById(id)) {
            shippingRepository.deleteById(id);
        }
    }

}
