package it.apuliadigital.shipping.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import it.apuliadigital.shipping.models.Shipping;

@Repository
public interface ShippingRepository extends CrudRepository<Shipping, Long> {

}
