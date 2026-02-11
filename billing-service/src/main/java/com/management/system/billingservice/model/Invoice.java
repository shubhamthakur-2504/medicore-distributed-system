package com.management.system.billingservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Invoice {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private UUID patientId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
