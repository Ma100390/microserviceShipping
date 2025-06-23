package it.apuliadigital.shipping.models;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import it.apuliadigital.shipping.enums.EnumStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Schema(description = "Modello per la spedizione")
public class Shipping {

    @Id
    @GeneratedValue
    @Schema(description = "Identificativo della spedizione", example = "1")
    private Long id;

    @Min(value = 1, message = "Il codice ordine deve essere maggiore di 0")
    @Schema(description = "Codice dell'ordine", example = "12345")
    private int orderCode;

    @NotNull(message = "Lo stato della spedizione è obbligatorio")
    @Schema(description = "Stato della spedizione", example = "IN_TRANSIT")
    private EnumStatus status;

    @NotBlank(message = "L'indirizzo di destinazione è obbligatorio")
    @Size(max = 255, message = "L'indirizzo non può superare i 255 caratteri")
    @Schema(description = "Indirizzo di destinazione", example = "Via Roma 1, Bari")
    private String destinationAddress;

    @NotBlank(message = "Il corriere è obbligatorio")
    @Size(max = 100, message = "Il nome del corriere non può superare i 100 caratteri")
    @Schema(description = "Corriere incaricato", example = "DHL")
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
