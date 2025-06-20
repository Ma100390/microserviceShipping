package it.apuliadigital.shipping.enums;

public enum EnumStatus {
    PENDING("In attesa"),
    IN_TRANSIT("In transito"),
    DELIVERED("Consegnato"),
    CANCELLED("Cancellato");

    private final String status;

    EnumStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }


}