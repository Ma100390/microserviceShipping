package it.apuliadigital.shipping.service;

import java.util.List;

import it.apuliadigital.shipping.models.Shipping;

public interface IShipping {
    List<Shipping> getAllShippings();
    Shipping getShippingById(Long id);
    Shipping createShipping(Shipping shipping);
    Shipping updateShipping(Shipping shipping);
    void deleteShipping(Long id);
}