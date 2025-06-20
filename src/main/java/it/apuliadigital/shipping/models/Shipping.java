package it.apuliadigital.shipping.models;

import java.util.Objects;

import it.apuliadigital.shipping.enums.EnumStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Shipping {
    @Id
    @GeneratedValue
    private Long id;
    private int orderCode;
    private EnumStatus status;
    private String destinationAddress;
    private String courier;

    public Shipping() {
        // Default constructor
    }

    public Shipping(Long id, int orderCode, EnumStatus status, String destinationAddress, String courier) {
        this.id = id;
        this.orderCode = orderCode;
        this.status = status;
        this.destinationAddress = destinationAddress;
        this.courier = courier;
    }

    public Long getId() {
        return id;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public EnumStatus getStatus() {
        return status;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Shipping)) {
            return false;
        }
        Shipping shipping = (Shipping) o;
        return Objects.equals(id, shipping.id) &&
               Objects.equals(status, shipping.status) &&
               Objects.equals(destinationAddress, shipping.destinationAddress) &&
               Objects.equals(courier, shipping.courier) &&
               orderCode == shipping.orderCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderCode, status, destinationAddress, courier);
    }
}
